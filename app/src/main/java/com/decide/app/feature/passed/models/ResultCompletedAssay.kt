package com.decide.app.feature.passed.models

data class ResultCompletedAssay(
    val date: Long,
    val shortResult: String,
    val result: String,
    val keyResult: List<Int> = listOf()
)
