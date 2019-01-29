package com.andresako.rocketx.data.response

import com.google.gson.annotations.SerializedName

data class LaunchDTO(
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("mission_name")
    val missionName: String,
    @SerializedName("launch_year")
    val launchYear: Int,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Int,
    @SerializedName("launch_date_utc")
    val launchDateUtc: String,
    @SerializedName("rocket")
    val rocket: RocketDetails,
    @SerializedName("launch_success")
    val launchSuccess: Boolean,
    @SerializedName("links")
    val links: Links
)

data class Links(
    @SerializedName("mission_patch_small")
    val missionPatchSmall: String
)

data class RocketDetails(
    @SerializedName("rocket_id")
    val rocketId: String,
    @SerializedName("rocket_name")
    val rocketName: String
)