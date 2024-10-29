package com.decide.app.feature.assay.assayMain.ui

data class AssayUI(
    val id: Int,
    val name: String,
    val idCategory: Int,
    val nameCategory: String,
    val countQuestions: String,
    val rating: String = "",
    val rated: Int = 0
)
