package com.example.capstonengaksoro.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstonengaksoro.data.remote.ApiService
import com.example.capstonengaksoro.data.response.ResponseNgaksoro
import com.example.capstonengaksoro.data.response.ResponseSoalItem
import com.example.capstonengaksoro.data.response.ResponseUploadImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NgaksoroRepository private constructor(
    private val apiService: ApiService
) {
    private val _nilai = MutableLiveData<Double>()
    val nilai: LiveData<Double> = _nilai

    private val _jawabanBenar = MutableLiveData<Double>()
    val jawabanBenar: LiveData<Double> = _jawabanBenar



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

    fun uploadImage(file: MultipartBody.Part, aksara: String): LiveData<ResponseUploadImage> {
        val data = MutableLiveData<ResponseUploadImage>()
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), aksara)
        val client = apiService.uploadImage(file, requestBody)
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

    fun getSoal(): LiveData<List<ResponseSoalItem>> {
        val data = MutableLiveData<List<ResponseSoalItem>>()
        try {
            val client = apiService.getSoal()
            client.enqueue(object : Callback<List<ResponseSoalItem>> {
                override fun onResponse(
                    call: Call<List<ResponseSoalItem>>,
                    response: Response<List<ResponseSoalItem>>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                        Log.d(TAG, "Response Sukses " + response.body().toString())
                    } else {
                        Log.d(TAG, "Response not successful: " + response.code())
                    }
                }

                override fun onFailure(call: Call<List<ResponseSoalItem>>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

        return data
    }

    fun hitungNilai(jumlahJawabanBenar: Double, jumlahSoal: Double ) : LiveData<Double> {
        val nilai = MutableLiveData<Double>()
        _jawabanBenar.value = jumlahJawabanBenar
        nilai.value = (jumlahJawabanBenar / jumlahSoal) * 100
        _nilai.value = nilai.value
        return nilai
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