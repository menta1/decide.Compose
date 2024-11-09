package com.decide.app.account.authenticationClient

import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.authenticationClient.exception.authExceptionMapper
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserDto
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthenticationClientImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val networkChecker: NetworkChecker,

    ) : FirebaseAuthenticationClient {

    override suspend fun isUserAuth(): Resource<UserDto, DecideAuthException> {
        if (networkChecker.isConnected()) {
            try {
                val user = firebaseAuth.currentUser
                return if (user != null) {
                    Resource.Success(
                        UserDto(
                            id = user.uid, email = user.email
                        )
                    )
                } else {
                    Resource.Error(
                        DecideAuthException.UserNotAuth(
                            "AuthException.UserNotAuth: Юзер не авторизован", "Не авторизован"
                        )
                    )
                }
            } catch (e: Exception) {
                return Resource.Error(
                    authExceptionMapper(
                        e
                    )
                )
            }
        } else {
            return Resource.Error(authExceptionMapper(DecideException.NoInternet()))
        }

    }

    override suspend fun singWithEmail(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    ) {
        if (checkerNetworkConnection(onResult)) {
            try {
                firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
                    .addOnSuccessListener { task ->
                        val userData = task.user
                        onResult(
                            Resource.Success(
                                UserDto(
                                    userData?.uid, userData?.email
                                )
                            )
                        )
                    }.addOnFailureListener {
                        onResult(
                            Resource.Error(
                                authExceptionMapper(
                                    it
                                )
                            )
                        )
                    }
            } catch (e: Exception) {
                Timber.tag("TAG").d("signInWithEmailAndPassword catch ${e.message}")
                onResult(
                    Resource.Error(
                        authExceptionMapper(
                            e
                        )
                    )
                )
            }

        } else {
            onResult(
                Resource.Error(
                    authExceptionMapper(
                        DecideAuthException.NoInternet()
                    )
                )
            )
        }
    }

    override suspend fun createUser(
        user: UserAuth,
        onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit
    ) {
        if (checkerNetworkConnection(onResult)) {
            try {
                firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnSuccessListener { task ->
                        val userData = task.user
                        userData?.sendEmailVerification()
                        onResult(
                            Resource.Success(
                                UserDto(
                                    userData?.uid, userData?.email
                                )
                            )
                        )
                    }.addOnFailureListener {
                        onResult(
                            Resource.Error(
                                authExceptionMapper(
                                    it
                                )
                            )
                        )
                    }
            } catch (e: Exception) {
                onResult(
                    Resource.Error(
                        authExceptionMapper(
                            e
                        )
                    )
                )
            }
        }
    }

    override suspend fun passwordReset(
        email: String,
        onResult: (response: Resource<Boolean, DecideAuthException>) -> Unit
    ) {
        if (checkerNetworkConnection {}) {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    onResult(Resource.Success(true))
                }
                .addOnFailureListener {
                    onResult(
                        Resource.Error(
                            authExceptionMapper(
                                it
                            )
                        )
                    )
                }
        } else {
            onResult(Resource.Error(authExceptionMapper(FirebaseNetworkException(""))))
        }
    }

    override suspend fun getUser() {

    }

    override suspend fun logOutUser() {
        firebaseAuth.signOut()
    }


    private fun checkerNetworkConnection(onResult: (response: Resource<UserDto, DecideAuthException>) -> Unit): Boolean {
        return if (networkChecker.isConnected()) {
            true
        } else {
            onResult(Resource.Error(authExceptionMapper(DecideAuthException.NoInternet())))
            false
        }
    }
}