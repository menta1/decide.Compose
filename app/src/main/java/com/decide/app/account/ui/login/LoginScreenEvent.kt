package com.decide.app.account.ui.login

sealed interface LoginScreenEvent {
    data object TryAuth : LoginScreenEvent
    data object AuthWithVK : LoginScreenEvent
    class SetEmail(val email: String) : LoginScreenEvent
    class SetPassword(val password: String) : LoginScreenEvent
    class EmailFocused(val focus: Boolean) : LoginScreenEvent
}