package com.decide.app.feature.profile.profileMain.ui

sealed interface ProfileScreenEvent {
    data object UpdateProfile : ProfileScreenEvent
}