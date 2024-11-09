package com.decide.app.activity.ui

import com.decide.uikit.theme.Themes

data class MainState(
    val isAuth: MainLogicState = MainLogicState.LOADING,
    val isFirstLaunch: Boolean = true,
    val theme: Themes = Themes.SYSTEM
)

enum class MainLogicState {
    LOADING,
    SUCCESS,
    ERROR
}
