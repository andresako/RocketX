package com.andresako.rocketx.data

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofitClient(baseUrl: String, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            GsonConverterFactory
                .create(
                    GsonBuilder()
                        .create()
                )
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
}

fun getOkHttpClient(context: Context): OkHttpClient {

    val requestInterceptor = Interceptor { chain ->

        val request = chain.request()
            .newBuilder()
            .build()

        return@Interceptor chain.proceed(request)
    }
    return OkHttpClient.Builder()
        .addInterceptor(ConnectivityInterceptor(context))
        .build()
}