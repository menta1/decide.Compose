package com.decide.app.feature.profile.profileMain.modal

import android.net.Uri
import com.decide.uikit.ui.statistic.modal.TemperamentUI

data class ProfileUI(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val avatar: Uri? = null,
    val anxiety: Pair<Float, Float>?,
    val temperament: TemperamentUI?
)
