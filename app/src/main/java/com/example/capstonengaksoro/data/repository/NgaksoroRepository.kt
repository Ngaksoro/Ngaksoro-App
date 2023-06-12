package com.example.capstonengaksoro.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstonengaksoro.data.remote.ApiService
import com.example.capstonengaksoro.data.response.ResponseNgaksoro
import com.example.capstonengaksoro.data.response.ResponseUploadImage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NgaksoroRepository private constructor(
    private val apiService: ApiService
) {

    fun getDataNgaksoro(): LiveData<ResponseNgaksoro> {
        val data = MutableLiveData<ResponseNgaksoro>()

        try {
            val client = apiService.getData()
            client.enqueue(object : Callback<ResponseNgaksoro> {
                override fun onResponse(
                    call: Call<ResponseNgaksoro>,
                    response: Response<ResponseNgaksoro>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ResponseNgaksoro>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
        return data
    }

    fun uploadImage(file: MultipartBody.Part)  : LiveData<ResponseUploadImage>{
        val data = MutableLiveData<ResponseUploadImage>()
        val client = apiService.uploadImage(file)
        client.enqueue(object : Callback<ResponseUploadImage> {
            override fun onResponse(
                call: Call<ResponseUploadImage>,
                response: Response<ResponseUploadImage>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        data.value = response.body()
                    } else {
                        data.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUploadImage>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message.toString()}")
            }
        })
        return data

    }


    companion object {
        private const val TAG = "NgaksoroRepository"

        @Volatile
        private var instance: NgaksoroRepository? = null
        fun getInstance(
            apiService: ApiService
        ): NgaksoroRepository =
            instance ?: synchronized(this) {
                instance ?: NgaksoroRepository(apiService)
            }.also { instance = it }
    }

}