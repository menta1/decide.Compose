package com.decide.app.database.local.dto

import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import kotlinx.serialization.Serializable

@Serializable
data class QuestionEntity(
    val id: Int,
    val text: String,
    val listAnswers: List<AnswerAssayEntity>,
    val image: String
)

fun QuestionEntity.toQuestionAssay(): QuestionAssay {
    return QuestionAssay(
        id, text, convertToAnswerAssay(listAnswers), image
    )
}

fun convertToQuestionAssay(questionAssay: List<QuestionEntity>): List<QuestionAssay> {
    return questionAssay.map { it.toQuestionAssay() }
}
