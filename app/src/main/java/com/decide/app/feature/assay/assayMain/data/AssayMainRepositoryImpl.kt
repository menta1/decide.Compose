package com.decide.app.feature.assay.assayMain.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.toAssay
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Надо обработать полученный результат и сохранить в БД, и передать в слой UI результат
 */
class AssayMainRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : AssayMainRepository {


    override fun getAssays(): Flow<Resource<List<Assay>, DecideException>> {
        return localStorage.assayDao()::getFlowAssays.invoke()
            .map { result ->
                if (result.isEmpty()) {
                    getAssays()
                }
                Resource.Success<List<Assay>, DecideException>(result.map { it.toAssay() })
            }
            .catch {
                Resource.Error<List<Assay>, DecideException>(
                    DecideException.NoFindElementDB(
                        it.message ?: "Нет такого элемента в БД",
                        "AssayWithResultRepositoryImpl getResult"
                    )
                )
            }
    }
}