package com.decide.app.feature.profile.profileMain.ui

import com.decide.app.feature.profile.profileMain.modal.ProfileUI

sealed interface ProfileState {

    data class Loaded(
        val profileUI: ProfileUI,
    ) : ProfileState

    data object Empty : ProfileState

    data object Loading : ProfileState

    data object NotAuthorized : ProfileState

    class Error(val message: String) : ProfileState

    companion object {
        val Initial: ProfileState = Loading
    }
}