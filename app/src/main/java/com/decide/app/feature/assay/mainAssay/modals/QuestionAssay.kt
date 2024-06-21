package com.decide.app.feature.assay.mainAssay.modals

import java.net.URL

data class QuestionAssay(
    val id: Int,
    val text: String,
    val listAnswers: List<AnswerAssay>,
    val image: String
)
