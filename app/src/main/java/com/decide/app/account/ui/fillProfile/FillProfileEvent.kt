package com.decide.app.account.ui.fillProfile

import android.net.Uri

sealed interface FillProfileEvent {
    data object Continue : FillProfileEvent
    class SetFirstName(val firstName: String) : FillProfileEvent
    class SetSecondName(val secondName: String) : FillProfileEvent
    class SetUriAvatar(val uri: Uri) : FillProfileEvent
    class SetDateOFBirth(val dateOfBirth: Long) : FillProfileEvent
    class SetGender(val gender: String) : FillProfileEvent
    class SearchCity(val city: String) : FillProfileEvent
    class SetCity(val city: String) : FillProfileEvent
}