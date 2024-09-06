package com.decide.app.feature.assay.assayProcess.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.ResultCompletedAssayEntity
import com.decide.app.database.local.entities.toAssay
import com.decide.app.database.local.entities.toKeyAssay
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AssayProcessRepositoryImpl @Inject constructor(
    private val localStorage: AppDatabase,
    private val remoteAssayStorage: RemoteAssayStorage
) : AssayProcessRepository {

    override suspend fun getAssay(id: Int): Resource<Assay, Exception> {
        remoteAssayStorage.getKey(id.toString())
        val result = localStorage.assayDao().getAssay(id)
        return Resource.Success(result.toAssay())
    }

    override suspend fun saveResult(
        id: Int,
        result: ResultCompletedAssay
    ) {
        val results = localStorage.assayDao()
            .getAssay(id).results.sortedBy { it.date }.toMutableList()

        if (results.size > 5) {
            results.removeLast()
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResults = result.shortResults,
                    results = result.results,
                    keyResults = result.keyResults
                )
            )
        } else {
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResults = result.shortResults,
                    results = result.results,
                    keyResults = result.keyResults
                )
            )
        }
        localStorage.assayDao().addNewResult(id, results)
    }

    override suspend fun getKeys(id: Int): KeyAssay {
        return localStorage.keyDao().getAssay(id).toKeyAssay()
    }
}