package com.decide.app.account.domain

import android.net.Uri
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserUpdate
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface AccountRepository {
    suspend fun createUser(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    )

    suspend fun isUserAuth(): String?
    fun saveAvatarFromGallery(uri: Uri)
    suspend fun getAvatar(): Resource<Uri, DecideException>
    suspend fun updateUser(userUpdate: UserUpdate): Resource<Boolean, DecideException>
    suspend fun singInUser(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    )

    suspend fun logOut(onResult: (result: Resource<Boolean, DecideException>) -> Unit)

    suspend fun passwordReset(
        email: String,
        onResult: (response: Resource<Boolean, DecideAuthException>) -> Unit
    )
}