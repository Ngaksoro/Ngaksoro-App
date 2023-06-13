package com.example.capstonengaksoro.ui.kuis.membaca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstonengaksoro.data.repository.NgaksoroRepository
import com.example.capstonengaksoro.data.response.ResponseSoalItem

class MembacaViewModel(private val ngaksoroRepository: NgaksoroRepository) : ViewModel() {

    private val _counterID = MutableLiveData<Int>()
    val counterID: LiveData<Int> = _counterID

    private val _isClicked = MutableLiveData<Boolean>()
    val isClicked: LiveData<Boolean> = _isClicked

    fun setIsClicked(value: Boolean) {
        _isClicked.value = value
    }


    fun getSoal(): LiveData<List<ResponseSoalItem>> {
        return ngaksoroRepository.getSoal()
    }


    init {
        _counterID.value = 1
        _isClicked.value = false
    }


    fun incrementCounterID() {
        _counterID.value = _counterID.value!! + 1
    }

    fun hitungNilai(jumlahJawabanBenar: Double, jumlahSoal: Double) : LiveData<Double> {
        return ngaksoroRepository.hitungNilai(jumlahJawabanBenar, jumlahSoal)
    }


}
