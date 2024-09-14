package com.decide.app.account.ui.registration

import com.decide.app.account.ui.validators.ValidationEmail
import com.decide.app.account.ui.validators.ValidationPassword


data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isErrorEmail: ValidationEmail = ValidationEmail.NO_ERROR,
    val isErrorPassword: ValidationPassword = ValidationPassword.NO_ERROR,
    val isErrorPasswordConfirm: ValidationPassword = ValidationPassword.NO_ERROR,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    REGISTRATION,
    SUCCESS_REGISTRATION,
    DATA_ENTRY,
    NO_INTERNET,
    UNKNOWN_ERROR
}
