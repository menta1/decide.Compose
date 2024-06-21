package com.decide.app.database.remote.assay

import android.util.Log
import com.decide.app.database.remote.assay.dto.AssayDTO
import com.decide.app.database.remote.assay.dto.CategoryDTO
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class RemoteAssayStorageImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val firebaseFirestore: FirebaseFirestore,
) : RemoteAssayStorage {


    override suspend fun getAssay(id: String): Flow<Result<AssayDTO>> = flow {
        if (!networkChecker.isConnected()) {
            emit(Result.failure(Throwable("No internet")))
        }

        firebaseFirestore.collection(EXAMS).document(id).collection("ASSAY")
            .document("ASSAY")
            .snapshots()
            .map {
                Result.success(it.toObject(AssayDTO::class.java))
            }
            .catch {
                Result.failure<Exception>(it)
            }
    }

    override suspend fun getAssays(onResult: (Resource<List<AssayDTO>>) -> Unit) {
        if (!networkChecker.isConnected()) {
            onResult(Resource.Error(Exception("No internet")))
        }
        try {
            firebaseFirestore.collection(EXAMS).get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    onResult(
                        Resource.Success(task.result
                            .map {
                                it.toObject(AssayDTO::class.java)
                            })
                    )
                }.addOnFailureListener {
                    onResult(Resource.Error(it))
                }
        } catch (e: Exception) {
            onResult(Resource.Error(e))
        }
    }

    override fun getCategories(): Flow<Result<List<CategoryDTO>>> = flow {
        if (!networkChecker.isConnected()) {
            emit(Result.failure(Throwable("No internet")))
        }
        firebaseFirestore.collection(CATEGORIES).snapshots()
            .map {
                Result.success(listOf(it.toObjects(CategoryDTO::class.java)))
            }
            .catch {
                Result.failure<Exception>(it)
            }
    }

    companion object {
        const val COLLECTION_USERS = "USERS"
        const val EXAMS_SHORT = "EXAMS_SHORT"
        const val EXAMS = "EXAMS"
        const val EXAM = "EXAM"
        const val CATEGORIES = "CATEGORIES"
        const val KEY = "KEY"
        const val TAG = "TAG"
        const val CATEGORY_IMAGE_STORAGE = "category"

        const val USER_ID = "userId"
        const val USER_RESULT_EXAMS = "RESULTS_EXAMS"

        const val USER_ID_DEF = "-1"

    }
}