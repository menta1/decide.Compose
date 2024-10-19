package com.decide.app.database.remote

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.data.AccountRepositoryImpl.Companion.KEY_USER_EMAIL
import com.decide.app.account.data.AccountRepositoryImpl.Companion.KEY_USER_ID
import com.decide.app.account.modal.UserUpdate
import com.decide.app.account.statisticsClient.StatisticsClient
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.dto.AccountDTO
import com.decide.app.database.remote.dto.AssayDTO
import com.decide.app.database.remote.dto.CategoryDTO
import com.decide.app.database.remote.dto.KeyDto
import com.decide.app.database.remote.dto.toAssayEntity
import com.decide.app.database.remote.dto.toCategoryEntity
import com.decide.app.database.remote.dto.toKeyEntity
import com.decide.app.database.remote.dto.toProfileEntity
import com.decide.app.database.remote.exceptions.DecideDatabaseException
import com.decide.app.database.remote.exceptions.DecideStorageException
import com.decide.app.database.remote.exceptions.firestoreExceptionMapper
import com.decide.app.database.remote.exceptions.storageExceptionMapper
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class RemoteAssayStorageImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val remoteDatabase: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    @ApplicationContext private val context: Context,
    private val dataStore: DataStore<Preferences>,
    private val statisticsClient: StatisticsClient
) : RemoteAssayStorage {

    override suspend fun getAssay(id: String): Flow<Result<AssayDTO>> = flow {
        if (!networkChecker.isConnected()) {
            emit(Result.failure(Throwable("No internet")))
        }

        remoteDatabase.collection(EXAMS).document(id).collection("ASSAY").document("ASSAY")
            .snapshots().map {
                Result.success(it.toObject(AssayDTO::class.java))
            }.catch {
                Result.failure<Exception>(it)
            }
    }

    @SuppressLint("LogNotTimber")
    override suspend fun getAssays(onResult: (result: Resource<Boolean, DecideException>) -> Unit) {
        remoteDatabase.collection(EXAMS).get().addOnCompleteListener { task: Task<QuerySnapshot> ->
            coroutineScope.launch {
                localStorage.assayDao().insert(task.result.map {
                    it.toObject(AssayDTO::class.java).toAssayEntity()
                })
                onResult(Resource.Success(true))
                Log.d("FIREBASE", "FIREBASE SUCCESS = getAssays")
            }
        }.addOnFailureListener {
            onResult(Resource.Error(firestoreExceptionMapper(it)))
            Log.d("FIREBASE", "FIREBASE ERRORS = $it")
        }
    }

    @SuppressLint("LogNotTimber")
    override suspend fun getCategories() {
        try {
            remoteDatabase.collection(CATEGORIES).get()
                .addOnSuccessListener { task ->
                    coroutineScope.launch {
                        localStorage.categoryDao().insert(task.map {
                            it.toObject(CategoryDTO::class.java).toCategoryEntity()
                        })
                    }
                }.addOnFailureListener {
                    Log.d("FIREBASE", "FIREBASE ERRORS = $it")
                }
        } catch (e: Exception) {
            Log.d("FIREBASE", "FIREBASE ERRORS = ${e.message}")
        }

    }

    override suspend fun getKey(id: String) {
        try {
            remoteDatabase.collection(KEYS).document(id).get()
                .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    coroutineScope.launch {
                        task.result.toObject(KeyDto::class.java)
                            ?.let { localStorage.keyDao().insert(it.toKeyEntity()) }
                    }
                }.addOnFailureListener {
                    Timber.tag("FIREBASE").d("FIREBASE ERRORS getKey = ${it.message}")
                }
        } catch (e: Exception) {
            Timber.tag("FIREBASE").d("FIREBASE ERRORS getKey = ${e.message}")
        }

    }

    override suspend fun createAccount(
        account: AccountDTO,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        try {
            remoteDatabase.collection(USERS).document(account.id).set(account)
                .addOnSuccessListener {
                    onResult(Resource.Success(true))
                }.addOnFailureListener {
                    onResult(Resource.Error(firestoreExceptionMapper(it)))
                }
        } catch (e: Exception) {
            onResult(Resource.Error(firestoreExceptionMapper(e)))
        }
    }

    override suspend fun updateAccount(
        userUpdate: UserUpdate,
        id: String
    ): Resource<Boolean, DecideDatabaseException> =
        suspendCoroutine { continuation ->

            try {
                remoteDatabase.collection(USERS).document(id).update(
                    "firstName",
                    userUpdate.firstName,
                    "lastName",
                    userUpdate.lastName,
                    "dateBirth",
                    userUpdate.dateBirth,
                    "city",
                    userUpdate.city,
                    "gender", userUpdate.gender
                ).addOnSuccessListener {
                    continuation.resume(Resource.Success(true))
                }.addOnFailureListener {
                    continuation.resume(
                        Resource.Error(
                            firestoreExceptionMapper(it)
                        )
                    )
                    Timber.tag("FIREBASE").d("FIREBASE ERRORS updateAccount = ${it.message}")
                }
            } catch (e: Exception) {
                continuation.resume(Resource.Error(firestoreExceptionMapper(e)))
                Timber.tag("FIREBASE").d("FIREBASE ERRORS updateAccount = ${e.message}")
            }

        }


    override suspend fun saveAvatar(
        inputStream: InputStream?,
        id: String
    ) {
        if (inputStream != null) {
            try {
                withContext(Dispatchers.IO) {
                    storage.reference.child(id).child("avatar.jpg").putStream(inputStream)
                        .addOnSuccessListener { task ->
                            Timber.tag("FIREBASE").d("FIREBASE Success")
                        }.addOnFailureListener {
                            Timber.tag("FIREBASE").d(it.message ?: "FIREBASE Success")
                            // storageExceptionMapper(it)
                        }
                }
            } catch (e: Exception) {
                Timber.tag("FIREBASE").d(e.message ?: "FIREBASE Success")
//                storageExceptionMapper(e)
            }
        } else {
            DecideStorageException.InputStream("InputStream = null", "InputStream = null")
        }

    }

    override suspend fun getPassedAssays(id: String) {
        try {
            remoteDatabase.collection(USERS).document(id).collection(PASSED_ASSAYS).get()
                .addOnSuccessListener { documents ->
                    documents.documents.map { it.toObject(AssayDTO::class.java) }
                        .forEach { assayDTO ->
                            if (assayDTO != null) {
                                coroutineScope.launch {
                                    localStorage.assayDao().updateAssay(assayDTO.toAssayEntity())
                                }
                                Timber.tag("FIREBASE").d("FIREBASE getPassedAssays Success")
                            }
                        }

                }.addOnFailureListener {
                    Timber.tag("FIREBASE").d(it.message ?: "FIREBASE getPassedAssays Fail")
                }
        } catch (e: Exception) {
            Timber.tag("FIREBASE").d(e.message ?: "FIREBASE getPassedAssays catch")
        }

    }

    override suspend fun putPassedAssays(id: Int) {
        coroutineScope.launch {
            try {
                val userId = dataStore.data.map { it[KEY_USER_ID] }.first()
                if (userId != null) {
                    val newPassedAssay = localStorage.assayDao().getAssay(id)
                    remoteDatabase.collection(USERS).document(userId).collection(PASSED_ASSAYS)
                        .document(id.toString()).set(newPassedAssay)
                        .addOnSuccessListener {
                            Timber.tag("FIREBASE").d("FIREBASE putPassedAssays Success")
                        }
                        .addOnFailureListener {
                            Timber.tag("FIREBASE").d(it.message ?: "FIREBASE putPassedAssays Fail")
                        }
                } else {
                    Timber.tag("FIREBASE").d("FIREBASE putPassedAssays userId != null")
                }

            } catch (e: Exception) {
                Timber.tag("FIREBASE").d(e.message ?: "FIREBASE putPassedAssays catch")
            }

        }
    }

    private fun downloadAvatar(
        id: String,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar.jpg")
        try {
            storage.reference.child(id).child("avatar.jpg").getFile(file).addOnSuccessListener {
                coroutineScope.launch {
                    dataStore.edit {
                        it[KEY_AVATAR] = file.toURI().toString()
                    }
                    onResult(Resource.Success(true))
                }
            }.addOnFailureListener {
                onResult(Resource.Success(false))
            }
        } catch (e: Exception) {
            onResult(Resource.Error(storageExceptionMapper(e)))
        }
    }

    override suspend fun getAccountData(
        id: String?,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        if (!id.isNullOrEmpty()) {
            try {
                remoteDatabase.collection(USERS).document(id).get()
                    .addOnSuccessListener { document ->
                        val profile = document.toObject(AccountDTO::class.java)
                        if (profile != null) {
                            Timber.tag("TAG").d("remoteDatabase getAccountData = profile == null")
                            coroutineScope.launch {
                                localStorage.profileDao().insert(
                                    profile.toProfileEntity()
                                )
                                downloadAvatar(
                                    id = profile.id, onResult = onResult
                                )
                                dataStore.edit { userSettings ->
                                    userSettings[KEY_USER_ID] = profile.id
                                    userSettings[KEY_USER_EMAIL] = profile.email
                                }
                                getPassedAssays(profile.id)
                            }

                        } else {
                            Timber.tag("TAG").d("remoteDatabase getAccountData = else")
                            onResult(
                                Resource.Error(
                                    firestoreExceptionMapper(
                                        DecideDatabaseException.NotFoundDocument(
                                            "DecideDatabaseException.NotFoundDocument: Нет данных пользователя в firebase",
                                            "Нет данных пользователя в firebase"
                                        )
                                    )
                                )
                            )
                        }

                    }.addOnFailureListener {
                        onResult(Resource.Error(firestoreExceptionMapper(it)))
                    }

                statisticsClient.getRemoteStatistic(id)

            } catch (e: Exception) {
                Timber.tag("TAG").d("remoteDatabase getAccountData = ${e.message}")
                onResult(Resource.Error(firestoreExceptionMapper(e)))

            }
        } else {
            onResult(Resource.Error(DecideAuthException.UserNotAuth("id.isNull", "Нет id")))
        }
    }

    companion object {
        const val EXAMS = "EXAMS"
        const val CATEGORIES = "CATEGORIES"
        const val KEYS = "KEYS"
        const val USERS = "USERS"
        const val PASSED_ASSAYS = "PASSED_ASSAYS"
        const val STATISTICS = "STATISTICS"
        const val ANXIETY = 1
        const val TEMPERAMENT = 2
        val KEY_AVATAR = stringPreferencesKey(
            name = "avatar"
        )
    }
}