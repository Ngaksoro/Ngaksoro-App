package com.example.capstonengaksoro.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityHomeBinding
import com.example.capstonengaksoro.ui.belajar.BelajarActivity
import com.example.capstonengaksoro.ui.kuis.PilihKuisActivity
import com.example.capstonengaksoro.utils.changeActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.home_activity)

        binding.modulBelajarCard.setOnClickListener {
            changeActivity(this, BelajarActivity::class.java)
        }

        binding.kuisCard.setOnClickListener {
            changeActivity(this, PilihKuisActivity::class.java)
        }
    }
}