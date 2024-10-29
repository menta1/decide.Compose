package com.decide.app.database.remote.exceptions

import com.decide.app.utils.DecideException

sealed class DecideDatabaseException(
    message: String,
    override val messageLog: String = "Unknown Error"
) : DecideException.FirestoneException(message, messageLog) {

    class NotFoundDocument(
        message: String = "NotFoundDocument",
        messageLog: String = "NotFoundDocument"
    ) : DecideDatabaseException(message, messageLog)

    class OutOfRange(
        message: String,
        messageLog: String
    ) : DecideDatabaseException(message, messageLog)

    class UnknownError(
        message: String = "UnknownError",
        messageLog: String = "UnknownError"
    ) : DecideDatabaseException(message, messageLog)
}