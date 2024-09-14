package com.decide.app.account.ui.validators

enum class ValidationEmail(
    val nameError: String,
    val isError: Boolean
) {
    INCORRECTLY(nameError = "Почта введена некорректно!", isError = true),
    CANT_EMPTY(nameError = "Почта не может быть пустая!", isError = true),
    USER_COLLISION(nameError = "Такой пользователь уже существует!", isError = true),
    NO_ERROR(nameError = "", isError = false)
}