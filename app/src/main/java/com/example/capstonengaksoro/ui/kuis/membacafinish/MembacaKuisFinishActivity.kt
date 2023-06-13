package com.example.capstonengaksoro.ui.kuis.membacafinish

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonengaksoro.databinding.ActivityMembacaKuisFinishBinding
import com.example.capstonengaksoro.ui.ViewModelFactory
import com.example.capstonengaksoro.ui.home.HomeActivity
import com.example.capstonengaksoro.ui.kuis.membaca.MembacaKuisActivity
import com.example.capstonengaksoro.utils.changeActivity

class MembacaKuisFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembacaKuisFinishBinding

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this.application)
    }

    private val viewModel: MembacaFinishViewModel by viewModels {
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembacaKuisFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        binding.btnFinish.setOnClickListener {
            changeActivity(this, HomeActivity::class.java)
            finish()
        }
        binding.btnTryAgain.setOnClickListener {
            changeActivity(this, MembacaKuisActivity::class.java)
            finish()
        }

        viewModel.getNilai().observe(this) {nilai ->
            binding.tvJumlahNilai.text = nilai.toString()

        }


        viewModel.getJumlahJawabanBenar().observe(this) {benar ->
            binding.tvJawabanBenar.text = benar.toInt().toString()

        }




    }
}