package com.example.capstonengaksoro.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonengaksoro.data.di.Injection
import com.example.capstonengaksoro.data.repository.NgaksoroRepository
import com.example.capstonengaksoro.ui.belajar.BelajarViewModel

class ViewModelFactory private constructor(private val ngaksoroRepository: NgaksoroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BelajarViewModel::class.java)) {
            return BelajarViewModel(ngaksoroRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}