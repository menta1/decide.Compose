package com.decide.app.feature.profile.settings.ui

sealed class SettingsScreenState {
    val isDarkTheme = false

    data object LogOut : SettingsScreenState()
    data object Loaded : SettingsScreenState()
    data object Loading : SettingsScreenState()
    class Error(val message: String) : SettingsScreenState()

    companion object {
        val Initial: SettingsScreenState = Loaded
    }
}