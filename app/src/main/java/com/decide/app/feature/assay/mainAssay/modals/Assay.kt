package com.decide.app.feature.assay.mainAssay.modals

data class Assay(
    val id: Int,
    val idCategory: Int,
    val name: String,
    val description: String,
    val nameCategory: String,
    val countQuestions: List<QuestionAssay>,
    val dateCreation: String,
    val rating: String,
    val type: Int
)
