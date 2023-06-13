package com.example.capstonengaksoro.ui.belajar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.data.response.ImagesItem
import com.example.capstonengaksoro.databinding.ActivityBelajarBinding
import com.example.capstonengaksoro.ui.ViewModelFactory
import com.example.capstonengaksoro.utils.checkInternetConnection
import com.example.capstonengaksoro.utils.networkStatusLiveData
import com.example.capstonengaksoro.utils.registerNetworkCallback

class BelajarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBelajarBinding
    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this.application)
    }
    private val belajarViewModel: BelajarViewModel by viewModels {
        factory
    }
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBelajarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.belajar_activity)
        // Customize the back button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_foward)
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        Init Network Checking
        registerNetworkCallback(this)

        //        Init ProgresBar
        progressBar = binding.progressBar

//        Init Rv

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)


        showLoading(true)
        showLostConnectionAnim(false)

        if (!checkInternetConnection(this)) {
            showLostConnectionAnim(true)
            Toast.makeText(
                this,
                resources.getString(R.string.check_connection),
                Toast.LENGTH_SHORT
            ).show()

        }

        networkStatusLiveData.observe(this, { isConnected ->
            if (isConnected) {
                // Koneksi internet terhubung
                // Lakukan tindakan yang sesuai
                belajarViewModel.getData().observe(this, { response ->
                    if (response != null) {
                        showLoading(false)
                        showLostConnectionAnim(false)
                        val adapter = BelajarAdapter(response.images, object : BelajarAdapter.OnItemClickCallback {
                            override fun onItemClick(data: ImagesItem) {
                                val dialogBuilder = AlertDialog.Builder(this@BelajarActivity)
                                val inflater = LayoutInflater.from(this@BelajarActivity)
                                val dialogView = inflater.inflate(R.layout.popup_layout, null)
                                dialogBuilder.setView(dialogView)

                                val photoViewPopup = dialogView.findViewById<ImageView>(R.id.photoViewPopup)
                                Glide.with(this@BelajarActivity)
                                    .asGif()
                                    .load(data.gif)
                                    .into(photoViewPopup)

                                val textTitle = dialogView.findViewById<TextView>(R.id.textTitle)

                                textTitle.text = data.text.uppercase()

                                val alertDialog = dialogBuilder.create()
                                alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                alertDialog.show()

                                Toast.makeText(this@BelajarActivity, "You Clicked ${data.text}", Toast.LENGTH_SHORT).show()
                            }
                        })

                        binding.recyclerView.adapter = adapter
                    }
                })
            } else {
                showLostConnectionAnim(true)
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
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


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showLostConnectionAnim(isLost: Boolean) {
        if (isLost) {
            binding.animationView.visibility = View.VISIBLE
        } else {
            binding.animationView.visibility = View.GONE
        }
    }
}
