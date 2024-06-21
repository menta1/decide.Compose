package com.decide.app.database.remote.assay.dto

data class CategoryDTO(
    val id: String = "",
    val name: String = "",
    val listAssays: List<AssayDTO> = emptyList(),
    val countAssays: String = "",
    val description: String = ""
)
