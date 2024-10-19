package com.decide.app.account.statisticsClient.modals

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
data class GlobalStatistics(
    @SerialName("members") val members: Int = 0,
    @SerialName("result") val result: Map<String, Double> = emptyMap(),
)
