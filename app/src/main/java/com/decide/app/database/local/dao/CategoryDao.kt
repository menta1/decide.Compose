package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category_table WHERE id = :id")
    suspend fun getCategory(id: Int): CategoryEntity

    @Query("SELECT * FROM category_table")
    fun getFlowCategory(): Flow<List<CategoryEntity>>

}