package com.decide.app.feature.profile.profileMain.modal

import android.net.Uri

data class ProfileHeader(
    val firstName: String,
    val lastName: String?,
    val avatar: Uri? = null
)
