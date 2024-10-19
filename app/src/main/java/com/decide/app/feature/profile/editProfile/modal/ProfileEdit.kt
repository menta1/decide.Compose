package com.decide.app.feature.profile.editProfile.modal

data class ProfileEdit(
    val firstName: String = "",
    val lastName: String = "",
    val dateBirth: Long = -1,
    val gender: String = "",
    val city: String = "",
)
