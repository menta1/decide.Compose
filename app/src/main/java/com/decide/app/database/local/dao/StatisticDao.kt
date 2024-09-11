package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.decide.app.database.local.entities.StatisticEntity

@Dao
interface StatisticDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(statisticEntity: StatisticEntity)

    @Query("SELECT * FROM statistics")
    suspend fun getAll(): List<StatisticEntity>

    @Query("SELECT * FROM statistics WHERE id = :id")
    suspend fun getStatistic(id: Int): StatisticEntity

    @Delete
    suspend fun deleteOldStatistics(statisticEntity: StatisticEntity)

    @Update
    suspend fun update(statisticEntity: StatisticEntity)
}