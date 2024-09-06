package com.decide.app.account.authenticationClient.exception

import com.decide.app.utils.DecideException

sealed class DecideAuthException(
    message: String,
    override val messageLog: String
) : DecideException.AuthenticationException(message, messageLog) {

    class DecideAuthUserCollision(//Такой пользователь уже существует
        message: String,
        messageLog: String
    ) : DecideAuthException(message, messageLog)

    class DecideAuthInvalidUser(//Неверно введены пароль или имейл
        message: String,
        messageLog: String
    ) : DecideAuthException(message, messageLog)

    class UserNotAuth(
        message: String,
        messageLog: String
    ) : DecideAuthException(message, messageLog)

    class NoInternet(
        message: String,
        messageLog: String
    ) : DecideAuthException(message, messageLog)

    class UnknownError(
        message: String,
        messageLog: String
    ) : DecideAuthException(message, messageLog)
}