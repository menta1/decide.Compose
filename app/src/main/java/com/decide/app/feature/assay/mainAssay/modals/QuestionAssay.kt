package com.decide.app.feature.assay.mainAssay.modals

data class QuestionAssay(
    val id: Int,
    val text: String,
    val listAnswers: List<AnswerAssay>,
    val image: String,
    val countResponses: Int
)
