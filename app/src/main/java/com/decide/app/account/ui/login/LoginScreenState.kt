package com.decide.app.account.ui.login

import com.decide.app.account.ui.registration.ValidationEmail
import com.decide.app.account.ui.registration.ValidationPassword

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isErrorEmail: ValidationEmail = ValidationEmail.NO_ERROR,
    val isErrorPassword: ValidationPassword = ValidationPassword.NO_ERROR,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    PROCESS_AUTH,
    SUCCESS_AUTH,
    DATA_ENTRY
}
