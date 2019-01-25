package com.andresako.rocketx.data

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnected()) {
            return chain.proceed(chain.request())
        } else {
            throw NoInternetException()
        }
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnected
    }
}

class NoInternetException : RuntimeException("You are not connected to internet")