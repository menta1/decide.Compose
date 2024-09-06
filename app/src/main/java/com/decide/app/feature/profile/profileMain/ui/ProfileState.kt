package com.decide.app.feature.profile.profileMain.ui

import com.decide.app.feature.profile.profileMain.modal.ProfileHeader

sealed interface ProfileState {

    data class Loaded(val profileHeader: ProfileHeader) : ProfileState

    data object Empty : ProfileState

    data object Loading : ProfileState

    data object NotAuthorized : ProfileState


    class Error(val message: String) : ProfileState

    companion object {
        val Initial: ProfileState = Loading
    }
}