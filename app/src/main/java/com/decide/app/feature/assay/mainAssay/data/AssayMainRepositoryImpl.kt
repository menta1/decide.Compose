package com.decide.app.feature.assay.mainAssay.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.toAssay
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Надо обработать полученный результат и сохранить в БД, и передать в слой UI результат
 */
class AssayMainRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : AssayMainRepository {


    override fun getAssays(): Flow<Resource<List<Assay>>> {

        coroutineScope.launch { remoteAssayStorage.getAssays() }

        return localStorage.assayDao()::getFlowAssays.invoke()
            .map { result ->
                Resource.Success(result.map { it.toAssay() })
            }
            .catch { Resource.Error(it) }
    }
}