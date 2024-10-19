package com.decide.app.account.exceptions

import com.decide.app.utils.DecideException

sealed class KladrException(
    message: String,
    override val messageLog: String
) : DecideException.KladrException(message, messageLog) {

    class BadRequest(
        message: String,
        messageLog: String
    ) : KladrException(message, messageLog)

    class NotFound(
        message: String,
        messageLog: String
    ) : KladrException(message, messageLog)


    class InternalServerError(
        message: String,
        messageLog: String
    ) : KladrException(message, messageLog)


    class Undefined(
        message: String,
        messageLog: String
    ) : KladrException(message, messageLog)

}