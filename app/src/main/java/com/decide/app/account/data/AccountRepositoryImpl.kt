package com.decide.app.account.data

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.decide.app.account.authenticationClient.AuthenticationClient
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserUpdate
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.profile.ProfileEntity
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.database.remote.dto.AccountDTO
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val authenticationClient: AuthenticationClient,
    private val remoteStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) : AccountRepository {

    override suspend fun isUserAuth(): String? {
        return when (val result = authenticationClient.isUserAuth()) {
            is Resource.Error -> null
            is Resource.Success -> {
                result.data.id
            }
        }
    }

    override fun saveAvatarFromGallery(uri: Uri) {
        val handler = CoroutineExceptionHandler { _, exception ->
            Timber.tag("TAG").d("saveAvatar Handler = %s", exception.message)
        }
        coroutineScope.launch(handler) {
            val file =
                File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar.jpg")
            val inputStream = context.contentResolver.openInputStream(uri)
            val userId = dataStore.data.map { it[KEY_USER_ID] }.first()
            remoteStorage.saveAvatar(
                context.contentResolver.openInputStream(uri),
                userId ?: throw Exception("userId=null")
            )

            inputStream.use { input ->
                FileOutputStream(file).use { output ->
                    input?.copyTo(output) ?: throw Exception("IOException")
                }
            }
            dataStore.edit {
                it[KEY_AVATAR] = file.toURI().toString()
            }
        }
    }


    override suspend fun getAvatar(): Resource<Uri, DecideException> {
        val uriAvatar = dataStore.data.map { it[KEY_AVATAR] }.first()
        return if (uriAvatar != null) {
            Resource.Success(uriAvatar.toUri())
        } else {
            Resource.Error(
                DecideException.NoFindIdAvatar(
                    message = "uriAvatar = null",
                    messageLog = "AccountRepositoryImpl-getAvatar=null"
                )
            )
        }
    }

    override suspend fun updateUser(userUpdate: UserUpdate) {
        coroutineScope.launch {
            val currentUserId = dataStore.data.map { it[KEY_USER_ID] }.first()
            if (!currentUserId.isNullOrBlank()) {

                var profile = localStorage.profileDao().get(currentUserId)
                if (profile != null) {

                    profile = profile.copy(
                        firstName = userUpdate.firstName.ifBlank { profile!!.firstName },
                        lastName = userUpdate.lastName.ifBlank { profile!!.lastName },
                        dateBirth = if (userUpdate.dateBirth != -1L) {
                            userUpdate.dateBirth
                        } else {
                            profile.dateBirth
                        },
                        city = userUpdate.city.ifBlank { profile!!.city },

                        )
                    localStorage.profileDao().insert(profile)
                    remoteStorage.updateAccount(
                        UserUpdate(
                            firstName = profile.firstName,
                            lastName = profile.lastName,
                            dateBirth = profile.dateBirth,
                            city = profile.city
                        ), currentUserId
                    )
                } else {
                    Timber.tag("TAG").d("updateUser profile == null")
                }

            } else {
                Timber.tag("TAG").d("updateUser = else")
            }
        }
    }

    override suspend fun singInUser(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        authenticationClient.singInUser(user) { response ->
            when (response) {
                is Resource.Error -> onResult(Resource.Error(response.error))
                is Resource.Success -> {
                    coroutineScope.launch {
                        remoteStorage.getAccountData(
                            id = response.data.id,
                            onResult = onResult
                        )
                    }
                }
            }
        }
    }

    override suspend fun logOut(onResult: (result: Resource<Boolean, DecideException>) -> Unit) {
        authenticationClient.logOutUser()
        coroutineScope.launch {
            localStorage.profileDao().deleteUserInfo()
            localStorage.assayDao().deleteAll()
            dataStore.edit {
                it.clear()
            }
            onResult(Resource.Success(true))
            remoteStorage.getAssays {}//для оптимизации нужно выставлять какой то индикатор чтобы не загружать часто с БД
        }
    }


    override suspend fun createUser(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        authenticationClient.createUser(
            user = user
        ) { response ->
            when (response) {
                is Resource.Success -> {
                    coroutineScope.launch {//Если успешно, то заранее проходим авторизацию
                        authenticationClient.singInUser(user) { responseAuth ->
                            when (responseAuth) {
                                is Resource.Success -> {
                                    Timber.tag("TAG").d("singInUser = RESPONSE_SUCCESS")
                                }

                                is Resource.Error -> {
                                    Timber.tag("TAG").d("singInUser = Failed")
                                }

                            }
                        }
                    }
                    val account = response.data

                    if (account.id.isNullOrEmpty() || account.email.isNullOrEmpty()) {
                        onResult(
                            Resource.Error(
                                DecideAuthException.UnknownError(
                                    "account id|email = null|empty",
                                    "Регистрация не удалась"
                                )
                            )
                        )
                    } else {
                        createUserRemoteBD(
                            id = account.id,
                            email = account.email,
                            onResult = { responseRemoteDB ->
                                when (responseRemoteDB) {
                                    is Resource.Success -> {
                                        createUserLocalBD(
                                            id = account.id,
                                            email = account.email,
                                            onResult = onResult
                                        )
                                    }

                                    is Resource.Error -> {
                                        onResult(Resource.Success(false))
                                    }
                                }
                            }
                        )
                    }
                }

                is Resource.Error -> {
                    onResult(Resource.Error(response.error))
                }
            }
        }


    }

    private fun createUserRemoteBD(
        id: String,
        email: String,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        coroutineScope.launch {
            remoteStorage.createAccount(
                account = AccountDTO(
                    id = id,
                    email = email,
                    firstName = email,
                    dateRegistration = Date().time
                ),
                onResult = onResult
            )
        }
    }

    private fun createUserLocalBD(
        id: String,
        email: String,
        onResult: (Resource<Boolean, DecideException>) -> Unit
    ) {
        Timber.tag("TAG").d("createUserLocalBD id = $id")
        Timber.tag("TAG").d("createUserLocalBD email = $email")
        coroutineScope.launch {
            dataStore.edit { userSettings ->
                userSettings[KEY_USER_ID] = id
                userSettings[KEY_USER_EMAIL] = email
            }
            localStorage.profileDao().insert(
                ProfileEntity(
                    id = id,
                    email = email,
                    firstName = email,
                    dateRegistration = Date().time
                )
            )
            onResult(Resource.Success(true))
        }
    }

    companion object {

        val KEY_USER_ID = stringPreferencesKey(
            name = "id"
        )
        val KEY_USER_EMAIL = stringPreferencesKey(
            name = "email"
        )
        val KEY_AVATAR = stringPreferencesKey(
            name = "avatar"
        )
    }
}