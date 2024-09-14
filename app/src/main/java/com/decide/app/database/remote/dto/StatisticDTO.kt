package com.decide.app.database.remote.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
data class StatisticDTO(
    @SerialName("id") val id: Int = 0,//ID статистики
    @SerialName("result") val result: Double = 0.0,//результат для статистики. Общее количество баллов всех тестов для статистики,
    @SerialName("oldResult") val oldResult: Double = 0.0,//результат для статистики. Общее количество баллов всех тестов для статистики,
    @SerialName("globalResults") val globalResults: Double = 0.0,
    @SerialName("users") val users: Int = 0,
)
