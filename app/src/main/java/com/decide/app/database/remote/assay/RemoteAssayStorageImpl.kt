package com.decide.app.database.remote.assay

import com.decide.app.database.remote.assay.dto.AssayDTO
import com.decide.app.database.remote.assay.dto.CategoryDTO
import com.decide.app.database.remote.assay.dto.KeyDto
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

    override suspend fun getCategories(onResult: (Resource<List<CategoryDTO>>) -> Unit) {
        if (!networkChecker.isConnected()) {
            onResult(Resource.Error(Exception("No internet")))
        }
        try {
            firebaseFirestore.collection(CATEGORIES).get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    onResult(
                        Resource.Success(task.result
                            .map {
                                it.toObject(CategoryDTO::class.java)
                            })
                    )
                }
                .addOnFailureListener {
                    onResult(Resource.Error(it))
                }
        } catch (e: Exception) {
            onResult(Resource.Error(e))
        }

    }

    override suspend fun getKey(id: String, onResult: (Resource<KeyDto>) -> Unit) {

        if (!networkChecker.isConnected()) {
            onResult(Resource.Error(Exception("No internet")))
        }

        try {
            firebaseFirestore.collection(KEYS).document(id).get()
                .addOnCompleteListener {
                    onResult(
                        Resource.Success(it.result.toObject(KeyDto::class.java)!!)
                    )
                }
                .addOnFailureListener {
                    onResult(Resource.Error(it))
                }
        } catch (e: Exception) {
            onResult(Resource.Error(e))
        }
    }

    companion object {
        const val EXAMS = "EXAMS"
        const val CATEGORIES = "CATEGORIES"
        const val KEYS = "KEYS"
    }
}