package com.example.capstonengaksoro.ui.kuis.membaca

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
            showProgressBar(true)
            viewModel.getSoal().observe(this) { responseSoalItems ->
                if (responseSoalItems.isNotEmpty()) {
                    if (id <= responseSoalItems.size) {
                        showProgressBar(false)
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
                        viewModel.hitungNilai(
                            jawabanBenar.toDouble(),
                            responseSoalItems.size.toDouble()
                        ).observe(this) {
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

    private fun koreksiSoal(jawaban: String) {
        var isButtonClicked = false
        originalButtonBGColor()

        binding.btnOption1.setOnClickListener {
            if (!isButtonClicked) {
                isButtonClicked = true
                viewModel.setIsClicked(true)
                if (binding.btnOption1.text == jawaban) {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Benar",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption1.setBackgroundColor(Color.GREEN) // Set button background color to Green
                    jawabanBenar++
                } else {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption1.setBackgroundColor(Color.RED) // Set button background color to red
                }
                disableOptionButtons()
            }
        }

        binding.btnOption2.setOnClickListener {
            if (!isButtonClicked) {
                isButtonClicked = true
                viewModel.setIsClicked(true)
                if (binding.btnOption2.text == jawaban) {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Benar",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption2.setBackgroundColor(Color.GREEN) // Set button background color to Green
                    jawabanBenar++
                } else {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption2.setBackgroundColor(Color.RED) // Set button background color to Green
                }
                disableOptionButtons()
            }
        }

        binding.btnOption3.setOnClickListener {
            if (!isButtonClicked) {
                isButtonClicked = true
                viewModel.setIsClicked(true)
                if (binding.btnOption3.text == jawaban) {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Benar",
                        Toast.LENGTH_SHORT
                    ).show()
                    jawabanBenar++
                    binding.btnOption3.setBackgroundColor(Color.GREEN) // Set button background color to Green
                } else {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption3.setBackgroundColor(Color.RED) // Set button background color to Green
                }
                disableOptionButtons()
            }
        }

        binding.btnOption4.setOnClickListener {
            if (!isButtonClicked) {
                isButtonClicked = true
                viewModel.setIsClicked(true)
                if (binding.btnOption4.text == jawaban) {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Benar",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption4.setBackgroundColor(Color.GREEN) // Set button background color to Green
                    jawabanBenar++
                } else {
                    Toast.makeText(
                        this@MembacaKuisActivity,
                        "Jawaban Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnOption4.setBackgroundColor(Color.RED) // Set button background color to Green
                }
                disableOptionButtons()
            }
        }


        binding.btnNext.setOnClickListener {
            if (isButtonClicked) {
                viewModel.incrementCounterID()
                viewModel.setIsClicked(false)
                originalButtonBGColor()
                enableOptionButtons()
                isButtonClicked = false
            } else {
                Toast.makeText(
                    this@MembacaKuisActivity,
                    getString(R.string.select_soal),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun disableOptionButtons() {
        binding.btnOption1.isEnabled = false
        binding.btnOption2.isEnabled = false
        binding.btnOption3.isEnabled = false
        binding.btnOption4.isEnabled = false
    }

    private fun enableOptionButtons() {
        binding.btnOption1.isEnabled = true
        binding.btnOption2.isEnabled = true
        binding.btnOption3.isEnabled = true
        binding.btnOption4.isEnabled = true
    }

    private fun originalButtonBGColor() {
        val colorResId = R.color.cream_btn
        val color = ContextCompat.getColor(this, colorResId)
        binding.btnOption1.setBackgroundColor(color)
        binding.btnOption2.setBackgroundColor(color)
        binding.btnOption3.setBackgroundColor(color)
        binding.btnOption4.setBackgroundColor(color)
    }


    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.optionsLayout.visibility = View.GONE
            binding.btnNext.visibility = View.GONE
            binding.image.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.optionsLayout.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
            binding.image.visibility = View.VISIBLE
        }

    }


}