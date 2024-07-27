package com.decide.app.database.local.dto

data class KeyEntity(
    val id: Int,
    val name: String,
    val titleCategoryEng: String,
    val result: HashMap<String, String>,
    val resultShort: HashMap<String, String>
)
