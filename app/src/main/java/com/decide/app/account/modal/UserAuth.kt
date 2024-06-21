package com.decide.app.account.modal

data class UserAuth(
    val email: String,
    val password: String,
    val name: String? = null
)
