package com.decide.app.database.local.dto

import androidx.room.PrimaryKey
import com.decide.app.feature.passed.models.ResultCompletedAssay
import kotlinx.serialization.Serializable

@Serializable
data class ResultCompletedAssayEntity(
    @PrimaryKey val date: Long,
    val shortResult: String,
    val result: String,
    val keyResult: List<Int>
)

fun ResultCompletedAssayEntity.toResultCompletedAssay() = ResultCompletedAssay(
    date = date,
    shortResult = shortResult,
    result = result,
    keyResult = keyResult
)
