package com.example.capstonengaksoro.ui.kuis

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityMenulisKuisBinding
import com.example.capstonengaksoro.ui.home.HomeActivity
import com.example.capstonengaksoro.utils.changeActivity
import java.io.OutputStream

class MenulisKuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenulisKuisBinding

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
                "my_drawing_${System.currentTimeMillis()}.jpg"
            )
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let {
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(it)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                }
                outputStream?.close()

                // Show a success message or perform any other operations
                Toast.makeText(this, "Save Image Sukses", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Handle the exception
                Toast.makeText(this, "Save Error ${e.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
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

    companion object {
        private const val WRITE_STORAGE_PERMISSION_REQUEST = 1
    }
}