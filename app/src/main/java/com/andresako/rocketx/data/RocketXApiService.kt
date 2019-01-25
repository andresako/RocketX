package com.andresako.rocketx.data

import com.andresako.rocketx.data.response.LaunchDTO
import com.andresako.rocketx.data.response.RocketDTO
import io.reactivex.Observable
import retrofit2.http.GET


const val API_BASE_URL = "https://api.spacexdata.com/v3/"

interface RocketApiService {

    @GET("rockets")
    fun getRockets(): Observable<List<RocketDTO>>

    @GET("launches")
    fun getLaunches(): Observable<List<LaunchDTO>>
}