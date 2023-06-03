package com.example.capstonengaksoro.ui.belajar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstonengaksoro.data.repository.NgaksoroRepository
import com.example.capstonengaksoro.data.response.ResponseNgaksoro

class BelajarViewModel(
    private val ngaksoroRepository: NgaksoroRepository
) : ViewModel() {

    fun getData(): LiveData<ResponseNgaksoro> = ngaksoroRepository.getDataNgaksoro()

}