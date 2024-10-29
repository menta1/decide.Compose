package com.decide.app.feature.assay.assayProcess.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.ResultCompletedAssayEntity
import com.decide.app.database.local.entities.assay.toAssay
import com.decide.app.database.local.entities.toKeyAssay
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class AssayProcessRepositoryImpl @Inject constructor(
    private val localStorage: AppDatabase,
    private val remoteAssayStorage: RemoteAssayStorage,
    private val dataStore: DataStore<Preferences>,
    private val networkChecker: NetworkChecker,
) : AssayProcessRepository {

    override suspend fun getAssay(id: Int): Resource<Assay, DecideException> {
        return if (networkChecker.isConnected()) {
            Resource.Success(localStorage.assayDao().getAssay(id).toAssay())
        } else {
            Resource.Error(DecideException.NoInternet())
        }
    }

    override suspend fun saveResult(
        id: Int,
        result: ResultCompletedAssay
    ) {
        val results =
            localStorage.assayDao().getAssay(id).results.sortedBy { it.date }.toMutableList()

        if (results.size > 5) {
            Timber.tag("TAG").d("saveResult > 5")
            results.removeAt(results.lastIndex)
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResults = result.shortResults,
                    results = result.results,
                    keyResults = result.keyResults,
                    resultForStatistic = result.resultForStatistic
                )
            )
        } else {
            Timber.tag("TAG").d("saveResult < 5")
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResults = result.shortResults,
                    results = result.results,
                    keyResults = result.keyResults,
                    resultForStatistic = result.resultForStatistic
                )
            )
        }

        localStorage.assayDao().addNewResult(id, results)
        remoteAssayStorage.putPassedAssays(id)
    }

    override suspend fun getKeys(id: Int): KeyAssay {
        return localStorage.keyDao().getAssay(id).toKeyAssay()
    }

}