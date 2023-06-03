package com.example.capstonengaksoro.ui.kuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityMembacaKuisBinding

class MembacaKuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembacaKuisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembacaKuisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Title Layout
        supportActionBar?.title = getString(R.string.membaca_kuis_activity)
        // Customize the back button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_foward)
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
}