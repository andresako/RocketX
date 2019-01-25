package com.andresako.rocketx.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andresako.rocketx.data.room.dao.RocketDAO
import com.andresako.rocketx.data.room.entity.RocketEntity

@Database(
    entities = [
        RocketEntity::class
    ], version = 1
)
abstract class RocketXDatabase : RoomDatabase() {
    abstract fun rocketDAO(): RocketDAO
}