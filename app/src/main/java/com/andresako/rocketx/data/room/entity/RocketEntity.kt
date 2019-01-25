package com.andresako.rocketx.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket")
data class RocketEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    @ColumnInfo(name = "active")
    var active: Boolean,
    @ColumnInfo(name = "country")
    var country: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "number_of_engine")
    var numberOfEngines: Int,
    @ColumnInfo(name = "rocket_id")
    var rocketId: String,
    @ColumnInfo(name = "rocket_name")
    var rocketName: String
) {
    constructor() : this(
        0, false, "", "", 0, "", ""
    )
}