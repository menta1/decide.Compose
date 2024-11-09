package com.decide.app.account.ui.registration

sealed interface RegistrationEvent {
    data object TryRegistration : RegistrationEvent
    data object AuthWithVK : RegistrationEvent
    class SetEmail(val email: String) : RegistrationEvent
    class SetPassword(val password: String) : RegistrationEvent
    class SetConfirmPassword(val password: String) : RegistrationEvent
    data object PrivacyPolicy : RegistrationEvent
}