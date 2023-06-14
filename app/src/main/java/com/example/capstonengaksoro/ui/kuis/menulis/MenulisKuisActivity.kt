package com.example.capstonengaksoro.ui.kuis.menulis

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityMenulisKuisBinding
import com.example.capstonengaksoro.ui.ViewModelFactory
import com.example.capstonengaksoro.utils.changeActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class MenulisKuisActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMenulisKuisBinding

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this.application)
    }

    private val viewModel: MenulisKuisViewModel by viewModels {
        factory
    }

    private lateinit var aksaraText: String

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenulisKuisBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set Title Layout
        supportActionBar?.title = getString(R.string.menulis_kuis_activity)
        // Customize the back button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_foward)
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel.aksaraJawa.observe(this, { aksaras ->
            // Generate a random index
            val randomIndex = (0 until aksaras.size).random()

            // Access the random aksara from the list
            aksaraText = aksaras[randomIndex]
            binding.pertanyaanAksara.text = aksaraText
        })



        binding.hapusBtn.setOnClickListener {
            binding.drawingView.clear()
        }

        binding.cekBtn.setOnClickListener {
            saveDrawingToGallery()
        }
    }

    // Override onOptionsItemSelected to handle the back button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click here
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveDrawingToGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveDrawingToGalleryApi29AndAbove()
        } else {
            saveDrawingToGalleryBelowApi29()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveDrawingToGalleryApi29AndAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_STORAGE_PERMISSION_REQUEST
            )
            return
        }

        val bitmap = createBitmapFromView(binding.drawingView)
        // Upload the image
        val file = createMultipartFromBitmap(bitmap)
        uploadImage(file)

        saveImageToGallery(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveDrawingToGalleryBelowApi29() {
        val bitmap = createBitmapFromView(binding.drawingView)
        saveImageToGallery(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                "my_drawing_${System.currentTimeMillis()}.png"
            )
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let {
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
                }
                outputStream?.close()

            } catch (e: Exception) {
                // Handle the exception
                Toast.makeText(this, "Save Error ${e.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, "Save Error ${e.message.toString()}")
            }
        }
    }

    private fun createBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val backgroundPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        canvas.drawPaint(backgroundPaint)
        view.draw(canvas)
        return bitmap
    }

    private fun uploadImage(file: MultipartBody.Part) {
        showProgresBar(true)
        Toast.makeText(
            this@MenulisKuisActivity,
            "Please Wait Processing Image ...",
            Toast.LENGTH_SHORT
        ).show()
        viewModel.uploadImage(file, binding.pertanyaanAksara.text.toString())
            .observe(this, { response ->
                response?.let {
                    if (it.result != null) {
                        showProgresBar(false)
                        if (it.result.equals("true")) {
                            Toast.makeText(
                                this@MenulisKuisActivity,
                                "Tulisan Benar",
                                Toast.LENGTH_SHORT
                            ).show()
                            changeActivity(this, MenulisBenarActivity::class.java)
                            finish()
                        } else {
                            Toast.makeText(
                                this@MenulisKuisActivity,
                                "Tulisan Salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            })
    }

    private fun showProgresBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }

    }

    private fun createMultipartFromBitmap(bitmap: Bitmap): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val requestBody =
            RequestBody.create("image/png".toMediaTypeOrNull(), byteArrayOutputStream.toByteArray())
        return MultipartBody.Part.createFormData("file", "image.png", requestBody)
    }


    companion object {
        private const val WRITE_STORAGE_PERMISSION_REQUEST = 1
        private val TAG = MenulisKuisActivity::class.java.simpleName
    }
}