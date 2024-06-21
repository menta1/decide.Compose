package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.dto.AssayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assays: List<AssayEntity>)

    @Query("SELECT * FROM assay_table WHERE id = :id")
    fun getAssay(id: Int): AssayEntity?
}