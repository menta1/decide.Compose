package com.decide.app.feature.assay.mainAssay.modals

data class Assay(
    val id: Int,
    val idCategory: Int,
    val name: String,
    val nameCategory: String,
    val description: String,
    val questions: List<QuestionAssay>,
    val dateCreation: String,
    val type: Int,
    val timeForTest: Long,
    val timeForQuestions: Long,
    val rating: String,
)