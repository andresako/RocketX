package com.andresako.rocketx.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class LaunchEntity(
    @PrimaryKey(autoGenerate = false)
    var flightNumber: Int,
    @ColumnInfo(name = "mission_name")
    var missionName: String,
    @ColumnInfo(name = "launch_year")
    var launchYear: Int,
    @ColumnInfo(name = "launch_date_utc")
    var launchDateUtc: String,
    @ColumnInfo(name = "launch_date_unix")
    var launchDateUnix: Int,
    @ColumnInfo(name = "launch_success")
    var launchSuccess: Boolean,
    @ColumnInfo(name = "mission_patch_small")
    var missionPatchSmall: String,
    @ColumnInfo(name = "rocket_id")
    var rocketId: String,
    @ColumnInfo(name = "rocket_name")
    var rocketName: String
) {
    constructor() : this(
        0, "", 0, "", 0, false, "", "", ""
    )
}