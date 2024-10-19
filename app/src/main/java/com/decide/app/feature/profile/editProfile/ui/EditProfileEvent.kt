package com.decide.app.feature.profile.editProfile.ui

import android.net.Uri

sealed interface EditProfileEvent {
    data object Continue : EditProfileEvent
    class SetFirstName(val firstName: String) : EditProfileEvent
    class SetSecondName(val secondName: String) : EditProfileEvent
    class SetUriAvatar(val uri: Uri) : EditProfileEvent
    class SetDateOFBirth(val dateOfBirth: Long) : EditProfileEvent
    class SetGender(val gender: String) : EditProfileEvent
    class SearchCity(val city: String) : EditProfileEvent
    class SetCity(val city: String) : EditProfileEvent
}