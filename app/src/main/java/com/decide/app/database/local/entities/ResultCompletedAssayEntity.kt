package com.decide.app.database.local.entities

import androidx.room.PrimaryKey
import com.decide.app.feature.passed.models.ResultCompletedAssay
import kotlinx.serialization.Serializable

@Serializable
data class ResultCompletedAssayEntity(
    @PrimaryKey val date: Long,
    val shortResults: List<String>,
    val results: List<String>,
    val keyResults: List<Int>
)

fun ResultCompletedAssayEntity.toResultCompletedAssay() = ResultCompletedAssay(
    date = date,
    shortResults = shortResults,
    results = results,
    keyResults = keyResults
)
