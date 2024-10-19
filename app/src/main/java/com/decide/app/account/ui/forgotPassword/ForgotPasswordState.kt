package com.decide.app.account.ui.forgotPassword

import com.decide.app.account.ui.validators.ValidationEmail

data class ForgotPasswordState(
    val email: String = "",
    val isErrorEmail: ValidationEmail = ValidationEmail.NO_ERROR,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    LOADING,
    SUCCESS,
    DATA_ENTRY,
    NO_INTERNET,
    UNKNOWN_ERROR
}