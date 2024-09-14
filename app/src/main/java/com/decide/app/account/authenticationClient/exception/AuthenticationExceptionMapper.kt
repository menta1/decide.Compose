package com.decide.app.account.authenticationClient.exception

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

fun authExceptionMapper(exception: Exception): DecideAuthException {
    return when (exception) {
        is FirebaseAuthUserCollisionException -> DecideAuthException.DecideAuthUserCollision(
            exception.message
                ?: "AuthException.AuthUserCollision: Такой пользователь уже существует",
            "Такой пользователь уже существует"
        )

        is FirebaseAuthInvalidUserException -> DecideAuthException.DecideAuthInvalidUser(
            exception.message ?: ("AuthException.AuthInvalidUser: Неверный логин или пароль"),
            "Неверный логин или пароль"
        )

        is FirebaseAuthInvalidCredentialsException -> DecideAuthException.DecideAuthInvalidUser(
            exception.message ?: ("AuthException.AuthInvalidUser: Неверный логин или пароль"),
            "Неверный логин или пароль"
        )

        is FirebaseNetworkException -> DecideAuthException.NoInternet(
            exception.message ?: "AuthException.NoInternet: Отсутствует подключение",
            "Отсутствует подключение"
        )


        else -> DecideAuthException.UnknownError(
            exception.message ?: "AuthException.UnknownError: Неизвестная ошибка",
            "Неизвестная ошибка"
        )
    }
}