package com.decide.app.account.ui.validators

enum class ValidationPassword(
    val nameError: String,
    val isError: Boolean
) {
    NOT_MATCH(nameError = "Пароли не совпадают!", isError = true),
    INVALID_CHARS(nameError = "Минимально 6 символов!", isError = true),
    NO_ERROR(nameError = "", isError = false)
}