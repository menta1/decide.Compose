package com.decide.app.utils

open class DecideException(
    message: String,
    open val messageLog: String = "Unknown Error"
) : Exception(message) {

    open class StorageException(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

    open class NoInternet(
        message: String = "No Internet",
        messageLog: String = "Отсутствует соединение"
    ) : DecideException(message, messageLog)

    open class FirestoneException(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

    open class AuthenticationException(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

    class NoFindIdAvatar(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

    class NoFindElementDB(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

    class UserNotAuthorization(
        message: String = "User not authorization",
        messageLog: String = "Пользователь не авторизован"
    ) : DecideException(message, messageLog)

    open class KladrException(
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)

}
