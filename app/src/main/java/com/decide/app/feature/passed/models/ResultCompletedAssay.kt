package com.decide.app.feature.passed.models

data class ResultCompletedAssay(
    val date: Long,
    val shortResults: List<String>,
    val results: List<String>,
    val keyResults: List<Int> = listOf(),
    val resultForStatistic: Double = -1.0
)
