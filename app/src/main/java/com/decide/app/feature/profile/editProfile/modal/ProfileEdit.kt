package com.decide.app.feature.profile.editProfile.modal

import android.net.Uri

data class ProfileEdit(
    val firstName: String = "",
    val lastName: String = "",
    val dateBirth: Long = -1,
    val gender: String = "",
    val city: String = "",
    val avatar: Uri? = null,
)
