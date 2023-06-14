package com.example.capstonengaksoro.ui.kuis.menulis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstonengaksoro.data.repository.NgaksoroRepository
import com.example.capstonengaksoro.data.response.ResponseUploadImage
import okhttp3.MultipartBody

class MenulisKuisViewModel(private val ngaksoroRepository: NgaksoroRepository) : ViewModel() {

    private val _aksaraJawa = MutableLiveData<List<String>>()
    val aksaraJawa: LiveData<List<String>> get() = _aksaraJawa

    init {
        _aksaraJawa.value = listOf(
            "ha", "na", "ca", "ra", "ka", "da", "ta", "sa", "wa", "la",
            "pa", "dha", "ja", "ya", "nya", "ma", "ga", "ba", "tha", "nga"
        )
    }


    fun uploadImage(file: MultipartBody.Part, aksara: String): LiveData<ResponseUploadImage> {
        return ngaksoroRepository.uploadImage(file, aksara)
    }

}