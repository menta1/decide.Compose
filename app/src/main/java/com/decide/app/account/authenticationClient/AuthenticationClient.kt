package com.decide.app.account.authenticationClient

import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserDto
import com.decide.app.utils.Resource
import com.decide.app.utils.Response

interface AuthenticationClient {
    suspend fun isUserAuth(): Resource<UserDto, DecideAuthException>
    suspend fun singInUser(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    )

    suspend fun createUser(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    )

    suspend fun passwordReset(): Response
    suspend fun getUser()
    suspend fun logOutUser()
}