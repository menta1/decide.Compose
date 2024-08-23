package com.decide.app.feature.assay.assayProcess

data class KeyAssay(
    val id: Int,
    val name: String,
    val titleCategoryEng: String,
    val result: Map<String, String>,
    val resultShort: Map<String, String>
)
