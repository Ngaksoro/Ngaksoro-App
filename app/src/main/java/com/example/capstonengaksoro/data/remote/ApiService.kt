package com.example.capstonengaksoro.data.remote

import com.example.capstonengaksoro.data.response.ResponseNgaksoro
import com.example.capstonengaksoro.data.response.ResponseSoalItem
import com.example.capstonengaksoro.data.response.ResponseUploadImage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("assets")
    fun getData(): Call<ResponseNgaksoro>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("aksara") aksara: RequestBody
    ): Call<ResponseUploadImage>

    @GET("soal")
    fun getSoal(): Call<List<ResponseSoalItem>>


}
