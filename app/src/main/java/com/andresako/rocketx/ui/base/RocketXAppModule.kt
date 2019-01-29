package com.andresako.rocketx.ui.base

import android.content.Context
import androidx.room.Room
import com.andresako.rocketx.data.RocketApiService
import com.andresako.rocketx.data.database.RocketXDatabase
import com.andresako.rocketx.data.getOkHttpClient
import com.andresako.rocketx.data.getRetrofitClient
import com.andresako.rocketx.ui.MainActivitySubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val API_BASE_URL = "https://api.spacexdata.com/v3/"

@Module(
    subcomponents = [
        MainActivitySubComponent::class
    ]
)

class RocketXAppModule {

    @Provides
    @Singleton
    fun getContext(application: RocketXApp): Context {
        return application
    }

    @Singleton
    @Provides
    fun getRocketXAPI(context: Context): RocketApiService {
        val okHttpClient = getOkHttpClient(context)
        return getRetrofitClient(API_BASE_URL, okHttpClient)
            .create(RocketApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRocketXDB(applicationContext: Context): RocketXDatabase {
        return Room.databaseBuilder(
            applicationContext,
            RocketXDatabase::class.java, "RocketXDB"
        )
            .build()
    }
}