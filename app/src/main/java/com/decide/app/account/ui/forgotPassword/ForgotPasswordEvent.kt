package com.decide.app.account.ui.forgotPassword

sealed interface ForgotPasswordEvent {
    class SetEmail(val email: String) : ForgotPasswordEvent
    data object SendToEmail : ForgotPasswordEvent
}