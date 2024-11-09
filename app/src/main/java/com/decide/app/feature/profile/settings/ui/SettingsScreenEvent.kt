package com.decide.app.feature.profile.settings.ui

import com.decide.uikit.theme.Themes

sealed interface SettingsScreenEvent {
    data object LogOut : SettingsScreenEvent
    data object PrivacyPolicy : SettingsScreenEvent
    data object SendEmail : SettingsScreenEvent
    data class SwitchTheme(val themes: Themes) : SettingsScreenEvent
}