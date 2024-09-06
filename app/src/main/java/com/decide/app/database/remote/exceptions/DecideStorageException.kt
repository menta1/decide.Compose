package com.decide.app.database.remote.exceptions

import com.decide.app.utils.DecideException

sealed class DecideStorageException(
    message: String,
    override val messageLog: String
) : DecideException.StorageException(message, messageLog) {

    class NotFoundDocument(
        message: String,
        messageLog: String
    ) : DecideStorageException(message, messageLog)

    class UnknownError(
        message: String,
        messageLog: String
    ) : DecideStorageException(message, messageLog)

    class InputStream(
        message: String,
        messageLog: String
    ) : DecideStorageException(message, messageLog)

}