package com.decide.app.feature.profile.settings.ui

//sealed class SettingsScreenState {
//    val isDarkTheme = false
//    var isShowAds = false
//
//    data object LogOut : SettingsScreenState()
//    data object Loaded : SettingsScreenState()
//    data object Loading : SettingsScreenState()
//    class Error(val message: String) : SettingsScreenState()
//
//    companion object {
//        val Initial: SettingsScreenState = Loaded
//    }
//}

data class SettingsScreenState(
    val isDarkTheme: Boolean = false,
    val isShowAds: Boolean = false,
    val uiState: SettingState = SettingState.Loaded
)

enum class SettingState { LogOut, Loaded, Loading, Error }
