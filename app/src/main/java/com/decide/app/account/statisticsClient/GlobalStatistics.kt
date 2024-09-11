package com.decide.app.account.statisticsClient

import kotlinx.serialization.SerialName

data class GlobalStatistics(
    @SerialName("members") val members: Int = 0,
    @SerialName("result") val result: Double = 0.0
)
