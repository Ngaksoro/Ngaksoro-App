package com.example.capstonengaksoro.data.di

import android.content.Context
import com.example.capstonengaksoro.data.remote.ApiConfig
import com.example.capstonengaksoro.data.repository.NgaksoroRepository

object Injection {
    fun provideRepository(context: Context): NgaksoroRepository {
        val apiService = ApiConfig.getApiService()
        return NgaksoroRepository.getInstance(apiService)
    }
}