package com.decide.app.account

import com.decide.app.utils.Response

interface AccountClient {
    suspend fun isUserAuth(): Response
    suspend fun singInUser(dto: Any, onResult: (Response) -> Unit)
    suspend fun singUpUser(dto: Any, result: (Response) -> Unit)
    suspend fun passwordReset(): Response
    suspend fun getUser()
}