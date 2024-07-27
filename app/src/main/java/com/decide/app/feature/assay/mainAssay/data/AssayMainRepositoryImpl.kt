package com.decide.app.feature.assay.mainAssay.data

import android.util.Log
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.dto.toAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Надо обработать полученный результат и сохранить в БД, и передать в слой UI результат
 */
class AssayMainRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localAssayStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : AssayMainRepository {
    
    override suspend fun getAssays(
        onSuccess: (List<Assay>) -> Unit,
        onError: (message: String) -> Unit
    ) {
        remoteAssayStorage.getAssays {
            when (it) {
                is Resource.Error -> {
                    onError(it.exception.toString())
                }

                is Resource.Success -> {

                    coroutineScope.launch {
                        localAssayStorage.assayDao().insert(it.data.map { assayDTO ->
                            Log.d("TAG", "AssayMainRepositoryImpl 35 ${it.data}")
                            assayDTO.toAssay().toAssayEntity()
                        })
                    }

                    onSuccess(it.data.map { assayDTO ->
                        Log.d("TAG", "AssayMainRepositoryImpl 42 = ${assayDTO.name}")
                        assayDTO.toAssay()
                    })
                }
            }
        }
    }
}