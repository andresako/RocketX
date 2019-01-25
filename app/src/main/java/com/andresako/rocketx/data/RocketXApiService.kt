package com.andresako.rocketx.data

import com.andresako.rocketx.data.response.LaunchDTO
import com.andresako.rocketx.data.response.RocketDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface RocketApiService {

    @GET("rockets")
    fun getRockets(): Observable<List<RocketDTO>>

    @GET("launches")
    fun getLaunches(): Observable<List<LaunchDTO>>
}