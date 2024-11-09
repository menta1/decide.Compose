package com.decide.app.account.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.decide.app.account.authenticationClient.FirebaseAuthenticationClient
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.modal.UserUpdate
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.profile.ProfileEntity
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.database.remote.dto.AccountDTO
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.refreshuser.VKIDGetUserCallback
import com.vk.id.refreshuser.VKIDGetUserFail
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccountRepositoryImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val firebaseAuthenticationClient: FirebaseAuthenticationClient,
    private val remoteStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>
) : AccountRepository {

    override suspend fun isUserAuth(): String? {
        return when (val result = firebaseAuthenticationClient.isUserAuth()) {
            is Resource.Error -> dataStore.data.map { it[KEY_USER_ID] }.firstOrNull()
            is Resource.Success -> {
                result.data.id
            }
        }
    }

    suspend fun saveImageToExternalStorage(
        url: String
    ) {
        withContext(Dispatchers.IO) {
            val imageLoader = context.imageLoader

            val request = ImageRequest.Builder(context).data(url).allowHardware(false).build()

            val result = imageLoader.execute(request)

            if (result is SuccessResult) {
                val bitmap = result.drawable.toBitmap()
                val file =
                    File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar.jpg")
                file.outputStream().use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                val userId = dataStore.data.map { it[KEY_USER_ID] }.first()
                remoteStorage.saveAvatar(
                    context.contentResolver.openInputStream(file.toUri()),
                    userId ?: throw Exception("userId=null")
                )

                dataStore.edit {
                    it[KEY_AVATAR] = file.toURI().toString()
                }
            } else {
                throw IOException("Failed to load image")
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

    override suspend fun updateUser(userUpdate: UserUpdate): Resource<Boolean, DecideException> =
        suspendCoroutine { continuation ->
            if (!networkChecker.isConnected()) {
                continuation.resume(Resource.Error(DecideException.NoInternet()))
            } else {
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
                                gender = userUpdate.gender
                            )
                            localStorage.profileDao().insert(profile)
                            val resultSaveRemote = remoteStorage.updateAccount(
                                userUpdate = UserUpdate(
                                    firstName = profile.firstName,
                                    lastName = profile.lastName,
                                    dateBirth = profile.dateBirth,
                                    city = profile.city,
                                    gender = profile.gender
                                ), id = currentUserId
                            )
                            when (resultSaveRemote) {
                                is Resource.Error -> {
                                    continuation.resume(Resource.Error(resultSaveRemote.error))
                                }

                                is Resource.Success -> {
                                    continuation.resume(Resource.Success(true))
                                }
                            }
                        } else {
                            continuation.resume(Resource.Error(DecideException.UserNotAuthorization()))
                        }

                    } else {
                        continuation.resume(Resource.Error(DecideException.UserNotAuthorization()))
                    }
                }
            }
        }

    override suspend fun singInUserWithEmail(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        firebaseAuthenticationClient.singWithEmail(user) { response ->
            when (response) {
                is Resource.Error -> onResult(Resource.Error(response.error))
                is Resource.Success -> {
                    coroutineScope.launch {
                        remoteStorage.getAssays { }
                        remoteStorage.getAccountData(
                            id = response.data.id, onResult = onResult
                        )
                    }
                }
            }
        }
    }

    override suspend fun singInUserWithVK(onResult: (result: Resource<Boolean, DecideException>) -> Unit) {

        val vkAuthCallback = object : VKIDAuthCallback {
            override fun onAuth(accessToken: AccessToken) {
                coroutineScope.launch {
                    remoteStorage.getAccountData(accessToken.userID.toString()) {
                        when (it) {
                            is Resource.Success -> {
                                onResult(Resource.Success(true))
                            }

                            is Resource.Error -> {
                                coroutineScope.launch {
                                    VKID.instance.getUserData(callback = object :
                                        VKIDGetUserCallback {
                                        override fun onSuccess(user: VKIDUser) {
                                            createUserRemoteBD(id = accessToken.userID.toString(),
                                                email = user.email ?: "",
                                                onResult = { responseRemoteDB ->
                                                    when (responseRemoteDB) {
                                                        is Resource.Success -> {
                                                            createUserLocalBD(id = accessToken.userID.toString(),
                                                                email = user.email ?: "",
                                                                onResult = {
                                                                    when (it) {
                                                                        is Resource.Success -> {
                                                                            coroutineScope.launch {
                                                                                user.photo200?.let { photo ->
                                                                                    saveImageToExternalStorage(
                                                                                        photo
                                                                                    )
                                                                                }

                                                                                onResult(
                                                                                    updateUser(
                                                                                        UserUpdate(
                                                                                            firstName = user.firstName,
                                                                                            lastName = user.lastName
                                                                                        )
                                                                                    )
                                                                                )
                                                                            }
                                                                        }

                                                                        is Resource.Error -> {

                                                                        }
                                                                    }
                                                                })
                                                        }

                                                        is Resource.Error -> {
                                                            onResult(Resource.Success(false))
                                                        }
                                                    }
                                                })
                                        }

                                        override fun onFail(fail: VKIDGetUserFail) {
                                            when (fail) {
                                                is VKIDGetUserFail.FailedApiCall -> fail.description // Использование текста ошибки.
                                                is VKIDGetUserFail.IdTokenTokenExpired -> fail.description // Использование текста ошибки.
                                                is VKIDGetUserFail.NotAuthenticated -> fail.description // Использование текста ошибки.
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }

            override fun onFail(fail: VKIDAuthFail) {
                when (fail) {
                    is VKIDAuthFail.Canceled -> { /*...*/
                    }

                    else -> {
                        //...
                    }
                }
            }
        }

        VKID.instance.authorize(callback = vkAuthCallback, params = VKIDAuthParams {
            scopes = setOf("email")
        })
    }

    override suspend fun logOut(onResult: (result: Resource<Boolean, DecideException>) -> Unit) {
        firebaseAuthenticationClient.logOutUser()
        coroutineScope.launch {
            localStorage.profileDao().deleteUserInfo()
            localStorage.assayDao().deleteAll()
            localStorage.statisticsDao().deleteAll()
            dataStore.edit {
                it.clear()
            }
            onResult(Resource.Success(true))
            remoteStorage.getAssays {}
        }
    }

    override suspend fun passwordReset(
        email: String,
        onResult: (response: Resource<Boolean, DecideAuthException>) -> Unit
    ) {
        firebaseAuthenticationClient.passwordReset(
            email = email, onResult = onResult
        )
    }


    override suspend fun createUser(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        firebaseAuthenticationClient.createUser(
            user = user
        ) { response ->
            when (response) {
                is Resource.Success -> {
                    coroutineScope.launch {//Если успешно, то заранее проходим авторизацию
                        firebaseAuthenticationClient.singWithEmail(user) { responseAuth ->
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
                                    "account id|email = null|empty", "Регистрация не удалась"
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
                            })
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
                    id = id, email = email, firstName = email, dateRegistration = Date().time
                ), onResult = onResult
            )
        }
    }

    private fun createUserLocalBD(
        id: String,
        email: String,
        onResult: (Resource<Boolean, DecideException>) -> Unit
    ) {
        coroutineScope.launch {
            dataStore.edit { userSettings ->
                userSettings[KEY_USER_ID] = id
                userSettings[KEY_USER_EMAIL] = email
            }
            localStorage.profileDao().insert(
                ProfileEntity(
                    id = id, email = email, firstName = email, dateRegistration = Date().time
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