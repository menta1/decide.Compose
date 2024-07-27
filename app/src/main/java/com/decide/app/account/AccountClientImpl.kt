package com.decide.app.account

import android.content.SharedPreferences
import com.decide.app.account.modal.AuthResponse
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserDto
import com.decide.app.utils.Constants.INTERNET_PROBLEM
import com.decide.app.utils.Constants.RESPONSE_EXCEPTION
import com.decide.app.utils.Constants.RESPONSE_SUCCESS
import com.decide.app.utils.Constants.TYPE_PROBLEM
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Response
import com.google.firebase.auth.FirebaseAuth

class AccountClientImpl (
    private val firebaseAuth: FirebaseAuth,
    private val networkChecker: NetworkChecker,
    private val sharedPreferences: SharedPreferences
) : AccountClient {
    override suspend fun isUserAuth(): Response {
        return if (firebaseAuth.currentUser != null) {
            Response().apply { resultCode = RESPONSE_SUCCESS }
        } else {
            Response().apply { resultCode = RESPONSE_EXCEPTION }
        }
    }

    override suspend fun singInUser(dto: Any, onResult: (Response) -> Unit) {
        checkerNetworkType(dto = dto, onResult)

        firebaseAuth.signInWithEmailAndPassword((dto as UserAuth).email, dto.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result.user
                    onResult(
                        (AuthResponse(
                            UserDto(
                                user?.tenantId,
                                user?.email
                            )
                        ).apply { resultCode = RESPONSE_SUCCESS })
                    )
                } else {
                    onResult(Response().apply { resultCode = RESPONSE_EXCEPTION })
                }
            }
    }

    override suspend fun singUpUser(dto: Any, onResult: (Response) -> Unit) {
        checkerNetworkType(dto = dto, onResult)

        firebaseAuth.createUserWithEmailAndPassword(
            (dto as UserAuth).email, dto.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result.user
                onResult(
                    (AuthResponse(
                        UserDto(
                            user?.tenantId,
                            user?.email,
                            dto.name
                        )
                    ).apply { resultCode = RESPONSE_SUCCESS })
                )
            } else {
                onResult(Response().apply { resultCode = RESPONSE_EXCEPTION })
            }
        }
    }

    override suspend fun passwordReset(): Response {
        TODO("Not yet implemented")
    }

    override suspend fun getUser() {
        TODO("Not yet implemented")
    }

    private fun checkerNetworkType(dto: Any, onResult: (Response) -> Unit) {
        if (!networkChecker.isConnected()) {
            onResult(Response().apply { resultCode = INTERNET_PROBLEM })
        }

        if (dto !is UserAuth) {
            onResult(Response().apply { resultCode = TYPE_PROBLEM })
        }
    }
}