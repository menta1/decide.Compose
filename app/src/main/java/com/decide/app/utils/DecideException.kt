package com.decide.app.utils

open class DecideException(
    message: String,
    open val messageLog: String = "Unknown Error"
) : Exception(message) {

    open class StorageException(
        message: String,
        messageLog: String
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
        message: String,
        messageLog: String
    ) : DecideException(message, messageLog)


}
