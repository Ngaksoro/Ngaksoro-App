package com.example.capstonengaksoro.ui.kuis.menulis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonengaksoro.databinding.ActivityMenulisBenarBinding
import com.example.capstonengaksoro.ui.home.HomeActivity
import com.example.capstonengaksoro.utils.changeActivity

class MenulisBenarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenulisBenarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenulisBenarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnFinish.setOnClickListener {
            changeActivity(this, HomeActivity::class.java)
            finish()
        }
        binding.btnTryAgain.setOnClickListener {
            changeActivity(this, MenulisKuisActivity::class.java)
            finish()
        }
    }
}