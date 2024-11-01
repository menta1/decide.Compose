package com.decide.app.database.remote.dto

import androidx.annotation.Keep
import com.decide.app.database.local.entities.profile.ProfileEntity

@Keep
data class AccountDTO(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val dateBirth: Long = -1,
    val gender: String = "",
    val city: String = "",
    val dateRegistration: Long = -1
)

fun AccountDTO.toProfileEntity() = ProfileEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    dateBirth = dateBirth,
    gender = gender,
    city = city,
    dateRegistration = dateRegistration
)