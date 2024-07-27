package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.database.local.dto.ResultCompletedAssayEntity

@Dao
interface AssayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(assays: List<AssayEntity>)

    @Query("SELECT * FROM assay_table WHERE id = :id")
    fun getAssay(id: Int): AssayEntity

    @Query("UPDATE assay_table SET results =:newResult WHERE id = :id")
    fun addNewResult(id: Int, newResult: List<ResultCompletedAssayEntity>): Int

}