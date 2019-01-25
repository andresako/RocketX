package com.andresako.rocketx.data.response

import com.google.gson.annotations.SerializedName

data class RocketDTO(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("engines")
    val engines: Engine,
    @SerializedName("id")
    val id: Int,
    @SerializedName("rocket_id")
    val rocketId: String,
    @SerializedName("rocket_name")
    val rocketName: String
)

data class Engine(
    @SerializedName("number")
    val number: Int
)