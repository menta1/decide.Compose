package com.decide.app.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decide.app.database.local.dto.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categories: CategoryEntity)

    @Query("SELECT * FROM category_table WHERE id = :id")
    suspend fun getCategory(id: Int): CategoryEntity

}