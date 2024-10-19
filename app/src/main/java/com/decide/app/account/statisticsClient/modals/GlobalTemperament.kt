package com.decide.app.account.statisticsClient.modals

import androidx.annotation.Keep
import kotlinx.serialization.SerialName

@Keep
data class GlobalTemperament(
    @SerialName("members") val members: Int = 0,
    @SerialName("choleric") val choleric: Double = 0.0,
    @SerialName("sanguine") val sanguine: Double = 0.0,
    @SerialName("phlegmatic") val phlegmatic: Double = 0.0,
    @SerialName("melancholic") val melancholic: Double = 0.0,
)
