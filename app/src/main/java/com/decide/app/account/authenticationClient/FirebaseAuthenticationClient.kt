package com.decide.app.account.authenticationClient

import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserDto
import com.decide.app.utils.Resource

interface FirebaseAuthenticationClient {
    suspend fun isUserAuth(): Resource<UserDto, DecideAuthException>
    suspend fun singWithEmail(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    )

    suspend fun createUser(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    )

    suspend fun passwordReset(
        email: String,
        onResult: (response: Resource<Boolean, DecideAuthException>) -> Unit
    )

    suspend fun getUser()
    suspend fun logOutUser()
}