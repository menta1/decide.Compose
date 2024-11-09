package com.decide.app.database.local.entities.assay

import androidx.annotation.Keep
import com.decide.app.feature.assay.assayMain.modals.QuestionAssay
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class QuestionEntity(
    val id: Int,
    val text: String,
    val listAnswers: List<AnswerAssayEntity>,
    val image: String,
    val countResponses: Int
)

fun QuestionEntity.toQuestionAssay() = QuestionAssay(
    id = id,
    text = text,
    listAnswers = convertToAnswerAssay(listAnswers),
    image = image,
    countResponses = countResponses
)

fun convertToQuestionAssay(questionAssay: List<QuestionEntity>): List<QuestionAssay> {
    return questionAssay.map { it.toQuestionAssay() }
}
