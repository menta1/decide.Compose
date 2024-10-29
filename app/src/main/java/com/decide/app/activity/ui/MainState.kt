package com.decide.app.activity.ui

data class MainState(
    val isAuth: MainLogicState = MainLogicState.LOADING,
    val isFirstLaunch: Boolean = true
)

enum class MainLogicState {
    LOADING,
    SUCCESS,
    ERROR
}
