package com.decide.app.database.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    var image: String,
    val name: String,
    val nameEng: String,
    val colorBackground: String
)
