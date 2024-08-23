package com.decide.app.database.remote

import android.annotation.SuppressLint
import android.util.Log
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.dto.AssayDTO
import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.database.remote.assay.dto.toAssayEntity
import com.decide.app.database.remote.assay.dto.toKeyEntity
import com.decide.app.database.remote.dto.CategoryDTO
import com.decide.app.database.remote.dto.toCategoryEntity
import com.decide.app.utils.NetworkChecker
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class RemoteAssayStorageImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val firebaseFireStore: FirebaseFirestore,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO
    )
) : RemoteAssayStorage {


    override suspend fun getAssay(id: String): Flow<Result<AssayDTO>> = flow {
        if (!networkChecker.isConnected()) {
            emit(Result.failure(Throwable("No internet")))
        }

        firebaseFireStore.collection(EXAMS).document(id).collection("ASSAY").document("ASSAY")
            .snapshots().map {
                Result.success(it.toObject(AssayDTO::class.java))
            }.catch {
                Result.failure<Exception>(it)
            }
    }

    @SuppressLint("LogNotTimber")
    override suspend fun getAssays() {
        firebaseFireStore.collection(EXAMS).get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                coroutineScope.launch {
                    localStorage.assayDao().insert(task.result.map {
                        it.toObject(AssayDTO::class.java).toAssayEntity()
                    })
                }
            }
            .addOnFailureListener {
                Log.d("FIREBASE", "FIREBASE ERRORS = $it")
            }
    }

    @SuppressLint("LogNotTimber")
    override suspend fun getCategories() {
        firebaseFireStore.collection(CATEGORIES).get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                coroutineScope.launch {
                    localStorage.categoryDao().insert(task.result.map {
                        it.toObject(CategoryDTO::class.java).toCategoryEntity()
                    })
                }
            }.addOnFailureListener {
                Log.d("FIREBASE", "FIREBASE ERRORS = $it")
            }
    }

    override suspend fun getKey(id: String) {
        firebaseFireStore.collection(KEYS).document(id).get()
            .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                coroutineScope.launch {
                    task.result.toObject(KeyDto::class.java)
                        ?.let { localStorage.keyDao().insert(it.toKeyEntity()) }
                }
            }
            .addOnFailureListener {
                Log.d("FIREBASE", "FIREBASE ERRORS = $it")
            }
    }

    companion object {
        const val EXAMS = "EXAMS"
        const val CATEGORIES = "CATEGORIES"
        const val KEYS = "KEYS"
    }
}