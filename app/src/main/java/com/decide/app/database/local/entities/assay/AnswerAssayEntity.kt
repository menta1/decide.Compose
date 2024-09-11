package com.decide.app.database.local.entities.assay

import com.decide.app.feature.assay.assayMain.modals.AnswerAssay
import kotlinx.serialization.Serializable

@Serializable
data class AnswerAssayEntity(
    val id: Int,
    val text: String,
    val value: Float
)

fun AnswerAssayEntity.toAnswerAssay() = AnswerAssay(
    id = id,
    text = text,
    value = value
)

fun convertToAnswerAssay(answerAssay: List<AnswerAssayEntity>): List<AnswerAssay> {
    return answerAssay.map { it.toAnswerAssay() }
}