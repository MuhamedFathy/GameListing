package com.github.data.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Authored by Mohamed Fathy on 12 Mar, 2022.
 * Contact: muhamed.gendy@gmail.com
 */
@SuppressLint("MissingPermission")
@Suppress("DEPRECATION") fun Context.isNetworkAvailable(): Boolean {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val nw = connectivityManager.activeNetwork ?: return false
  val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
  return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    .or(actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    .or(actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}