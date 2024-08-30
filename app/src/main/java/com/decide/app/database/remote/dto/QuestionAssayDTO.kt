package com.decide.app.database.remote.dto

import com.decide.app.database.local.dto.QuestionEntity
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import kotlinx.serialization.SerialName

data class QuestionAssayDTO(
    @SerialName("id") val id: Int = 0,
    @SerialName("text") val text: String = "",
    @SerialName("listAnswers") val listAnswers: List<AnswerAssayDTO> = emptyList(),
    @SerialName("image") val image: String = "",
    @SerialName("countResponses") val countResponses: Int = 0
)

fun QuestionAssayDTO.toQuestionAssay() = QuestionAssay(
    id = id,
    text = text,
    listAnswers = convertToAnswerAssay(listAnswers),
    image = image,
    countResponses = countResponses
)

fun convertToQuestionAssay(questionAssay: List<QuestionAssayDTO>): List<QuestionAssay> {
    return questionAssay.map { it.toQuestionAssay() }
}

fun QuestionAssayDTO.toQuestionAssayEntity() = QuestionEntity(
    id = id,
    text = text,
    listAnswers = convertToAnswerAssayEntity(listAnswers),
    image = image,
    countResponses = countResponses
)

fun convertToQuestionEntity(questionAssay: List<QuestionAssayDTO>): List<QuestionEntity> {
    return questionAssay.map { it.toQuestionAssayEntity() }
}