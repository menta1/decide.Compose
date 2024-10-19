package com.decide.app.database.local.entities.assay

import androidx.annotation.Keep
import androidx.room.PrimaryKey
import com.decide.app.feature.passed.models.ResultCompletedAssay
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ResultCompletedAssayEntity(
    @PrimaryKey val date: Long,
    val shortResults: List<String>,
    val results: List<String>,
    val keyResults: List<Int>,
    val resultForStatistic: Double = -1.0
)

fun ResultCompletedAssayEntity.toResultCompletedAssay() = ResultCompletedAssay(
    date = date,
    shortResults = shortResults,
    results = results,
    keyResults = keyResults,
    resultForStatistic = resultForStatistic
)
