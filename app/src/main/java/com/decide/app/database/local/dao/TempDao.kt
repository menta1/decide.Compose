package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.dto.AssayEntity

@Dao
interface TempDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assay: AssayEntity)

    @Query("SELECT * FROM temp_table")
    fun getAssay(): AssayEntity

    @Delete
    fun delete(assay: AssayEntity)
}