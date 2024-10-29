package com.decide.app.activity.ui

sealed interface MainEvent {
    data object InitApp : MainEvent
    data object IsAuth : MainEvent

}