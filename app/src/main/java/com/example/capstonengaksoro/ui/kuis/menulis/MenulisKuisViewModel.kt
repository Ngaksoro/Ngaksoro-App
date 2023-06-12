package com.example.capstonengaksoro.ui.kuis.menulis

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstonengaksoro.data.repository.NgaksoroRepository
import com.example.capstonengaksoro.data.response.ResponseUploadImage
import okhttp3.MultipartBody

class MenulisKuisViewModel(private val ngaksoroRepository: NgaksoroRepository) : ViewModel() {

    fun uploadImage(file: MultipartBody.Part) : LiveData<ResponseUploadImage> {
        return ngaksoroRepository.uploadImage(file)
    }

}