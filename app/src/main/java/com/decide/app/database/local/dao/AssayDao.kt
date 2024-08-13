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
    suspend fun insert(assays: List<AssayEntity>)

    @Query("SELECT * FROM assay_table WHERE id = :id")
    suspend fun getAssay(id: Int): AssayEntity

    @Query("UPDATE assay_table SET results =:newResult WHERE id = :id")
    suspend fun addNewResult(id: Int, newResult: List<ResultCompletedAssayEntity>): Int

    @Query("SELECT * FROM assay_table WHERE idCategory = :idCategory")
    suspend fun fetchAllAssaysByIdCategory(idCategory: Int): List<AssayEntity>

    @Query("SELECT * FROM assay_table WHERE results IS NOT NULL AND results != '[]'")
    fun getAssaysWithNonEmptyResults(): List<AssayEntity>
}