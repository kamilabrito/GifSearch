package com.kickstarter.gifsearch.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
  companion object {
    fun hasNetWorkConnection(context: Context): Boolean {
      val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
  }
}
