package com.decide.app.feature.passed

data class PassedAssay(
    val id: Int,
    val idCategory: Int,
    val name: String,
    val nameCategory: String,
    val rating: String,
    val results: List<ResultCompletedAssay>
)
