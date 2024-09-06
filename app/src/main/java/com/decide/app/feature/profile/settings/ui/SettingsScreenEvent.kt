package com.decide.app.feature.profile.settings.ui

sealed interface SettingsScreenEvent {
    data object LogOut : SettingsScreenEvent
}