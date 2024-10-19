package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.entities.assay.AssayEntity
import com.decide.app.database.local.entities.assay.ResultCompletedAssayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assays: List<AssayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAssay(assay: AssayEntity)

    @Query("SELECT * FROM assay_table WHERE id = :id")
    suspend fun getAssay(id: Int): AssayEntity

    @Query("SELECT * FROM assay_table WHERE id = :id")
    fun getResultAssay(id: Int): Flow<AssayEntity>

    @Query("UPDATE assay_table SET rating =:star WHERE id = :id")
    fun setRating(
        id: Int,
        star: String
    )

    @Query("UPDATE assay_table SET results =:newResult WHERE id = :id")
    suspend fun addNewResult(
        id: Int,
        newResult: List<ResultCompletedAssayEntity>
    ): Int

    @Query("SELECT * FROM assay_table")
    suspend fun getAllAssays(): List<AssayEntity>

    @Query("SELECT * FROM assay_table WHERE idCategory = :idCategory")
    suspend fun fetchAllAssaysByIdCategory(idCategory: Int): List<AssayEntity>

    @Query("SELECT * FROM assay_table WHERE results IS NOT NULL AND results != '[]'")
    fun getAssaysWithNonEmptyResults(): List<AssayEntity>

    @Query("SELECT * FROM assay_table")
    fun getFlowAssays(): Flow<List<AssayEntity>>

    @Query("DELETE FROM assay_table")
    suspend fun deleteAll()
}