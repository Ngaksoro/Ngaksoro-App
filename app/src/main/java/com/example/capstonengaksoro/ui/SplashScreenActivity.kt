package com.example.capstonengaksoro.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonengaksoro.databinding.ActivitySplashScreenBinding
import com.example.capstonengaksoro.ui.home.HomeActivity


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        Handler(Looper.getMainLooper()).postDelayed({

//            if (UserTokenPref.isLoggedIn()) {
//                val intent = Intent(this, HomeActivity::class.java)
//                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
//                startActivity(intent)
//                finish()
//            } else {
//                val intent = Intent(this, OnBoardingActivity::class.java)
//                startActivity(intent)
//                finish()
//            }

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        private const val SPLASH_DELAY = 3000L
    }
}