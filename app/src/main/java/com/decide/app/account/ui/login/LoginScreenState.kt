package com.decide.app.account.ui.login

import com.decide.app.account.ui.validators.ValidationEmail
import com.decide.app.account.ui.validators.ValidationPassword

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isErrorEmail: ValidationEmail = ValidationEmail.NO_ERROR,
    val isErrorPassword: ValidationPassword = ValidationPassword.NO_ERROR,
    val exceptionAuth: Boolean = false,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    PROCESS_AUTH,
    SUCCESS_AUTH,
    ERROR,
    NETWORK_ERROR,
    DATA_ENTRY
}
