package com.decide.uikit.common

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ru",
    showBackground = true
)
@Preview(
    name = "Dark theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "ru",
)
annotation class MainPreview