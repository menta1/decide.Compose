package com.decide.app.database.local.dto

import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class QuestionAssayEntity(
    val id: Int,
    val text: String,
    val listAnswers: List<AnswerAssayEntity>,
    val image: String
)

fun QuestionAssayEntity.toQuestionAssay(): QuestionAssay {
    return QuestionAssay(
        id, text, convertToAnswerAssay(listAnswers), image
    )
}

fun convertToQuestionAssay(questionAssay: List<QuestionAssayEntity>): List<QuestionAssay> {
    return questionAssay.map { it.toQuestionAssay() }
}
