package com.decide.app.account.ui.fillProfile

import android.net.Uri

sealed interface FillProfileEvent {
    data object Continue : FillProfileEvent
    class SetFirstName(val firstName: String) : FillProfileEvent
    class SetSecondName(val secondName: String) : FillProfileEvent
    class SetUriAvatar(val uri: Uri) : FillProfileEvent
//    class SetConfirmPassword(val password: String) : FillProfileEvent
//    class EmailFocused(val focus: Boolean) : FillProfileEvent
}