package com.decide.app.database.remote.dto

import com.decide.app.database.local.entities.assay.ResultCompletedAssayEntity

data class ResultCompletedAssayDTO(
    val date: Long = -1,
    val shortResults: List<String> = emptyList(),
    val results: List<String> = emptyList(),
    val keyResults: List<Int> = emptyList(),
    val resultForStatistic: Double = -1.0
)

fun ResultCompletedAssayDTO.toResultCompletedAssayEntity() = ResultCompletedAssayEntity(
    date = date,
    shortResults = shortResults,
    results = results,
    keyResults = keyResults,
    resultForStatistic = resultForStatistic
)