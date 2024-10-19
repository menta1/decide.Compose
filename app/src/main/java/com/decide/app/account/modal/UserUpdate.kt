package com.decide.app.account.modal

data class UserUpdate(
    val firstName: String = "",
    val lastName: String = "",
    val dateBirth: Long = -1,
    val city: String = "",
    val gender: String = ""
)
