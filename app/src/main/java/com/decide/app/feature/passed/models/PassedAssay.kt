package com.decide.app.feature.passed.models

data class PassedAssay(
    val id: Int,
    val idCategory: Int,
    val name: String,
    val nameCategory: String,
    val rating: Float,
    val results: List<ResultCompletedAssay>
)
