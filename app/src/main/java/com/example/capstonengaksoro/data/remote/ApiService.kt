package com.example.capstonengaksoro.data.remote

import com.example.capstonengaksoro.data.response.ResponseNgaksoro
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("assets")
    fun getData(): Call<ResponseNgaksoro>

}
