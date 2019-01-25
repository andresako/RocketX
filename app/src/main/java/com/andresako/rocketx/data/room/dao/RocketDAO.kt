package com.andresako.rocketx.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.andresako.rocketx.data.room.entity.RocketEntity
import io.reactivex.Single

@Dao
interface RocketDAO {

    @Insert(onConflict = REPLACE)
    fun saveAll(rockets: List<RocketEntity>)

    @Query("SELECT * FROM rocket")
    fun getAllAsync(): Single<List<RocketEntity>>
}