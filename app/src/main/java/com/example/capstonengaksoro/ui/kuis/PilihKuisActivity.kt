package com.example.capstonengaksoro.ui.kuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityPilihKuisBinding
import com.example.capstonengaksoro.utils.changeActivity

class PilihKuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihKuisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihKuisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.test_quiz)
        // Customize the back button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_foward)
        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.kuisMembacaCard.setOnClickListener {
            changeActivity(this, MembacaKuisActivity::class.java)
        }

        binding.kuisMenulisCard.setOnClickListener {
            changeActivity(this, MenulisKuisActivity::class.java)

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
}