package com.decide.app.feature.assay.mainAssay.ui

import com.decide.app.database.remote.assay.dto.QuestionAssayDTO

data class AssayUI(
    val id: Int,
    val name: String,
    val nameCategory: String,
    val countQuestions: String,
    val rating: String
)
