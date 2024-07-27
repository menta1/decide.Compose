package com.decide.app.database.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResultCompletedAssayEntity(
    val date: String,
    val result: String,
)
