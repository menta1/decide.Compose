package com.decide.app.account.ui.registration


data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isErrorEmail: ValidationEmail = ValidationEmail.NO_ERROR,
    val isErrorPassword: ValidationPassword = ValidationPassword.NO_ERROR,
    val isErrorPasswordConfirm: ValidationPassword = ValidationPassword.NO_ERROR,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class ValidationPassword(
    val nameError: String,
    val isError: Boolean
) {
    NOT_MATCH(nameError = "Пароли не совпадают!", isError = true),
    INVALID_CHARS(nameError = "Минимально 6 символов!", isError = true),
    NO_ERROR(nameError = "", isError = false)
}

enum class ValidationEmail(
    val nameError: String,
    val isError: Boolean
) {
    INCORRECTLY(nameError = "Почта введена некорректно!", isError = true),
    CANT_EMPTY(nameError = "Почта не может быть пустая!", isError = true),
    USER_COLLISION(nameError = "Такой пользователь уже существует!", isError = true),
    NO_ERROR(nameError = "", isError = false)
}

enum class UIState {
    REGISTRATION,
    SUCCESS_REGISTRATION,
    DATA_ENTRY,
    NO_INTERNET,
    UNKNOWN_ERROR
}
