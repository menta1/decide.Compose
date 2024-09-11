package com.decide.app.database.remote.dto

import com.decide.app.database.local.entities.assay.AnswerAssayEntity
import com.decide.app.feature.assay.assayMain.modals.AnswerAssay
import kotlinx.serialization.SerialName

data class AnswerAssayDTO(
    @SerialName("id") val id: Int = 0,
    @SerialName("text") val text: String = "",
    @SerialName("value") val value: Float = 0f
)

fun AnswerAssayDTO.toAnswerAssay() = AnswerAssay(
    id = id,
    text = text,
    value = value
)

fun convertToAnswerAssay(answerAssay: List<AnswerAssayDTO>): List<AnswerAssay> {
    return answerAssay.map { it.toAnswerAssay() }
}

fun AnswerAssayDTO.toAnswerAssayEntity() = AnswerAssayEntity(
    id = id,
    text = text,
    value = value
)

fun convertToAnswerAssayEntity(answerAssay: List<AnswerAssayDTO>): List<AnswerAssayEntity> {
    return answerAssay.map { it.toAnswerAssayEntity() }
}