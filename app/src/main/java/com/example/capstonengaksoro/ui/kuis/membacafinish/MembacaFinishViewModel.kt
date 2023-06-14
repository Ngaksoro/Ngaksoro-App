package com.example.capstonengaksoro.ui.kuis.membacafinish

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstonengaksoro.data.repository.NgaksoroRepository

class MembacaFinishViewModel(private val ngaksoroRepository: NgaksoroRepository) : ViewModel() {

    fun getNilai(): LiveData<Double> {
        return ngaksoroRepository.nilai
    }

    fun getJumlahJawabanBenar(): LiveData<Double> {
        return ngaksoroRepository.jawabanBenar
    }

}