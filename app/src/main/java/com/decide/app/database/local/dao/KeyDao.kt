package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.entities.KeyEntity

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(assays: KeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assays: List<KeyEntity>)

    @Query("SELECT * FROM keys_table WHERE id = :id")
    suspend fun getAssay(id: Int): KeyEntity
}