package com.decide.app.database.local.dto

import androidx.room.PrimaryKey
import com.decide.app.feature.passed.ResultCompletedAssay
import kotlinx.serialization.Serializable

@Serializable
data class ResultCompletedAssayEntity(
    @PrimaryKey val date: String,
    val shortResult: String,
    val result: String,
)

fun ResultCompletedAssayEntity.toResultCompletedAssay() = ResultCompletedAssay(
    date = date,
    shortResult = shortResult,
    result = result
)
