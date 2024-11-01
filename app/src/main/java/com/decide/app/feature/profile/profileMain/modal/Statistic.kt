package com.decide.app.feature.profile.profileMain.modal

data class Statistic(
    val id: Int = 0,
    val result: Double = 0.0,
    val globalResults: Map<String, Double> = emptyMap(),
    val users: Int = 0,
    val date: Long
)