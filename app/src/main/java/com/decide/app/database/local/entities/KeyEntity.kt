package com.decide.app.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.feature.assay.assayProcess.KeyAssay

@Entity(tableName = "keys_table")
data class KeyEntity(
    @PrimaryKey val id: Int,
    val idCategory: Int,
    val name: String,
    val titleCategoryEng: String,
    val result: Map<String, String>,
    val resultShort: Map<String, String>
)

fun KeyEntity.toKeyAssay() = KeyAssay(
    id = id,
    idCategory = idCategory,
    name = name,
    titleCategoryEng = titleCategoryEng,
    result = result,
    resultShort = resultShort
)
