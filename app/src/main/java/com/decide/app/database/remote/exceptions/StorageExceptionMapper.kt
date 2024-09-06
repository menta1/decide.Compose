package com.decide.app.database.remote.exceptions

import com.google.firebase.storage.StorageException

fun storageExceptionMapper(exception: Exception): DecideStorageException {
    return when ((exception as StorageException).errorCode) {

        StorageException.ERROR_BUCKET_NOT_FOUND -> {
            TODO()
        }

        StorageException.ERROR_CANCELED -> {
            TODO()
        }

        StorageException.ERROR_INVALID_CHECKSUM -> {
            TODO()
        }

        StorageException.ERROR_NOT_AUTHENTICATED -> {
            TODO()
        }

        StorageException.ERROR_NOT_AUTHORIZED -> {
            TODO()
        }

        StorageException.ERROR_OBJECT_NOT_FOUND -> {
            DecideStorageException.NotFoundDocument(
                exception.message ?: "NotFoundDocument: Обьект не найден", "Такого объекта нет"
            )
        }

        StorageException.ERROR_PROJECT_NOT_FOUND -> {
            TODO()
        }

        StorageException.ERROR_QUOTA_EXCEEDED -> {
            TODO()
        }

        StorageException.ERROR_RETRY_LIMIT_EXCEEDED -> {
            TODO()
        }


        else -> {
            DecideStorageException.UnknownError(
                exception.message ?: "UnknownError: Неизвестная ошибка", "Неизвестная ошибка"
            )
        }
    }
}