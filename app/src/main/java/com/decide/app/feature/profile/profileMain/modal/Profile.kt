package com.decide.app.feature.profile.profileMain.modal

import com.decide.app.feature.passed.models.PassedAssay

data class Profile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val dateBirth: Long,
    val city: String,
    val dateRegistration: String,
    val listAssayPassed: List<PassedAssay>
)