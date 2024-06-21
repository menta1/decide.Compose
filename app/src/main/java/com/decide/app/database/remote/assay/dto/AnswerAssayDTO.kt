package com.decide.app.database.remote.assay.dto

import com.decide.app.feature.assay.mainAssay.modals.AnswerAssay
import kotlinx.serialization.SerialName

data class AnswerAssayDTO(
    @SerialName("id")  val id: Int = 0,
    @SerialName("text")  val text: String = "",
    @SerialName("value")  val value: Int = 0
)
fun AnswerAssayDTO.toAnswerAssay(): AnswerAssay {
    return AnswerAssay(
        id, text, value
    )
}


fun convertToAnswerAssay(answerAssay: List<AnswerAssayDTO>): List<AnswerAssay> {
    return answerAssay.map { it.toAnswerAssay() }
}