package com.decide.app.database.remote.exceptions

import com.google.firebase.firestore.FirebaseFirestoreException

fun firestoreExceptionMapper(exception: Exception): DecideDatabaseException {
    return when ((exception as FirebaseFirestoreException).code) {
        FirebaseFirestoreException.Code.NOT_FOUND -> {
            DecideDatabaseException.NotFoundDocument(
                exception.message ?: "DecideDatabaseException.NotFoundDocument: Объект не найден",
                "Объект не найден"
            )
        }

        FirebaseFirestoreException.Code.OUT_OF_RANGE -> {
            DecideDatabaseException.OutOfRange(
                exception.message
                    ?: "DecideDatabaseException.OutOfRange: Операция за пределами допустимого диапазона",
                "Операция за пределами допустимого диапазона"
            )
        }

        else -> {
            DecideDatabaseException.UnknownError(
                exception.message ?: "DecideDatabaseException.UnknownError: Неизвестная ошибка",
                "Неизвестная ошибка"
            )
        }
    }
}