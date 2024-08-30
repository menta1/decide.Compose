package com.decide.app.feature.assay.assayProcess.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.ResultCompletedAssayEntity
import com.decide.app.database.local.dto.toAssay
import com.decide.app.database.local.dto.toKeyAssay
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AssayProcessRepositoryImpl @Inject constructor(
    private val localStorage: AppDatabase,
    private val remoteAssayStorage: RemoteAssayStorage
) : AssayProcessRepository {

    override suspend fun getAssay(id: Int): Resource<Assay> {
        remoteAssayStorage.getKey(id.toString())
        val result = localStorage.assayDao().getAssay(id)
        return if (result != null) {
            Resource.Success(result.toAssay())
        } else {
            Resource.Error(Exception("Ничего не найдено"))
        }
    }

    override suspend fun saveResult(id: Int, result: ResultCompletedAssay) {
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