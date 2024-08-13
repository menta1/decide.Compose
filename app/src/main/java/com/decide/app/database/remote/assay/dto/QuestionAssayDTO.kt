package com.decide.app.database.remote.assay.dto

import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import kotlinx.serialization.SerialName

data class QuestionAssayDTO(
    @SerialName("id") val id: Int = 0,
    @SerialName("text") val text: String = "",
    @SerialName("listAnswers") val listAnswers: List<AnswerAssayDTO> = emptyList(),
    @SerialName("image") val image: String = ""
)

fun QuestionAssayDTO.toQuestionAssay(): QuestionAssay {
    return QuestionAssay(
        id, text, convertToAnswerAssay(listAnswers), image
    )
}

fun convertToQuestionAssay(questionAssay: List<QuestionAssayDTO>): List<QuestionAssay> {
    return questionAssay.map { it.toQuestionAssay() }
}