package com.decide.app.account.statisticsClient

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
data class GlobalStatistics(
    @SerialName("members") val members: Int = 0,
    @SerialName("result") val result: Double = 0.0
)
