package com.example.capstonengaksoro.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private val _networkStatusLiveData = MutableLiveData<Boolean>()
val networkStatusLiveData: LiveData<Boolean>
    get() = _networkStatusLiveData

// Test
fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun registerNetworkCallback(context: Context) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkRequest = NetworkRequest.Builder().build()

    connectivityManager.registerNetworkCallback(networkRequest,
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkStatusLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                _networkStatusLiveData.postValue(false)
            }
        })
}

fun changeActivity(context: Context, targetActivity: Class<*>) {
    val intent = Intent(context, targetActivity)
    context.startActivity(intent)
}
