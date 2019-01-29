package com.andresako.rocketx.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.andresako.rocketx.data.room.entity.LaunchEntity
import io.reactivex.Single

@Dao
interface LaunchDAO {

    @Query("Select * from launch where rocket_id like :rocketId")
    fun getLaunchesForRocketId(rocketId: String): Single<List<LaunchEntity>>

    @Insert(onConflict = REPLACE)
    fun saveAll(launches: List<LaunchEntity>)
}