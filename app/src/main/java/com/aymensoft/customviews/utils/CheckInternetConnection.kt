package com.aymensoft.customviews.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.HttpURLConnection
import java.net.URL

fun checkForInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false

    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

fun checkForServerConnection(): Boolean {
    return try {
        val url = URL(GOOGLE_URL)
        val urlConnection: HttpURLConnection = url
            .openConnection() as HttpURLConnection
        urlConnection.setRequestProperty("Connection", "close")
        urlConnection.connectTimeout = 2000 // Timeout 2 seconds.
        urlConnection.connect()
        urlConnection.responseCode == 200
    }catch (e: Exception){
        false
    }
}