package com.decide.app.database.local.dto

import com.decide.app.feature.assay.mainAssay.modals.AnswerAssay
import kotlinx.serialization.Serializable

@Serializable
data class AnswerAssayEntity(
    val id: Int,
    val text: String,
    val value: Float
)

fun AnswerAssayEntity.toAnswerAssay(): AnswerAssay {
    return AnswerAssay(
        id, text, value
    )
}


fun convertToAnswerAssay(answerAssay: List<AnswerAssayEntity>): List<AnswerAssay> {
    return answerAssay.map { it.toAnswerAssay() }
}