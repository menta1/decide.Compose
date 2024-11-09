package com.decide.app.feature.profile.settings.domain

import com.decide.uikit.theme.Themes

interface SwitchThemesUseCase {
    suspend fun switchTheme(themes: Themes)
}