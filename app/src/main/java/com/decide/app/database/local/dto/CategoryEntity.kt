package com.decide.app.database.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.feature.category.mainCategory.modals.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val nameEng: String,
    val colorBackground: String,
    val description: String,
    val countAssays: Int,
)

fun CategoryEntity.toCategory() = Category(
    id = id,
    name = name,
    nameEng = nameEng,
    colorBackground = colorBackground,
    description = description,
    countAssays = countAssays
)