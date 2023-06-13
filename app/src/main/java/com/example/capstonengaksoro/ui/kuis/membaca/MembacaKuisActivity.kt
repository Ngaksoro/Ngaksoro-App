package com.example.capstonengaksoro.ui.kuis.membaca

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.databinding.ActivityMembacaKuisBinding
import com.example.capstonengaksoro.ui.ViewModelFactory
import com.example.capstonengaksoro.ui.kuis.membacafinish.MembacaKuisFinishActivity
import com.example.capstonengaksoro.utils.changeActivity

class MembacaKuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembacaKuisBinding

    private var jawabanBenar: Int = 0

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this.application)
    }


    private val viewModel: MembacaViewModel by viewModels {
        factory
    }

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

        viewModel.counterID.observe(this) { id ->
            viewModel.getSoal().observe(this) { responseSoalItems ->
                if (responseSoalItems.isNotEmpty()) {
                    if (id <= responseSoalItems.size) {
                        val currentSoal = responseSoalItems.firstOrNull { it.id == id }
                        binding.tvSoalNumber.text = "${id}/${responseSoalItems.size}"
                        currentSoal?.let { soal ->
                            Glide.with(this)
                                .load(soal.image)
                                .apply(RequestOptions().override(100, 100))
                                .into(binding.image)

                            binding.btnOption1.text = soal.opsi[0]
                            binding.btnOption2.text = soal.opsi[1]
                            binding.btnOption3.text = soal.opsi[2]
                            binding.btnOption4.text = soal.opsi[3]

                            // Koreksi Soal Jika Click
                            koreksiSoal(soal.jawaban)

                        }

                    } else {
                        viewModel.hitungNilai(jawabanBenar.toDouble(), 20.0).observe(this) {
                            Log.d("MembacaKuisActivity", it.toString())
                        }

                        changeActivity(this, MembacaKuisFinishActivity::class.java)
                        finish()
                    }
                } else {
                    Log.d("MembacaKuisActivity", "Empty response")
                }
            }
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

    fun koreksiSoal(jawaban: String) {
        viewModel.isClicked.observe(this) { isClicked ->
            if (!isClicked) {
                binding.btnOption1.setOnClickListener {
                    viewModel.setIsClicked(true)
                    if (binding.btnOption1.text == jawaban) {
                        Log.d("MembacaKuisActivity", "true")
                        jawabanBenar++
                    } else {
                        Log.d("MembacaKuisActivity", "false")
                    }
                }
                binding.btnOption2.setOnClickListener {
                    viewModel.setIsClicked(true)
                    if (binding.btnOption2.text == jawaban) {
                        Log.d("MembacaKuisActivity", "true")
                        jawabanBenar++
                    } else {
                        Log.d("MembacaKuisActivity", "false")
                    }
                }
                binding.btnOption3.setOnClickListener {
                    viewModel.setIsClicked(true)
                    if (binding.btnOption3.text == jawaban) {
                        Log.d("MembacaKuisActivity", "true")
                        jawabanBenar++
                    } else {
                        Log.d("MembacaKuisActivity", "false")
                    }
                }
                binding.btnOption4.setOnClickListener {
                    viewModel.setIsClicked(true)
                    if (binding.btnOption4.text == jawaban) {
                        Log.d("MembacaKuisActivity", "true")
                        jawabanBenar++
                    } else {
                        Log.d("MembacaKuisActivity", "false")
                    }
                }
            } else {
                binding.btnNext.setOnClickListener {
                    viewModel.incrementCounterID()
                    viewModel.setIsClicked(false)
                }
            }
        }

    }
}