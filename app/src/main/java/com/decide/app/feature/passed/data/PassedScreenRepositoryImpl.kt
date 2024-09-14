package com.decide.app.feature.passed.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.toPassedAssay
import com.decide.app.feature.passed.models.PassedAssay
import timber.log.Timber
import javax.inject.Inject

class PassedScreenRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase
) : PassedScreenRepository {
    override suspend fun fetchAllResults(): List<PassedAssay> {
        Timber.tag("TAG").d("PassedScreenRepositoryImpl fetchAllResults")
        return localAssayStorage.assayDao().getAssaysWithNonEmptyResults()
            .map {//надо наверное брать с профиля
                Timber.tag("TAG").d("PassedScreenRepositoryImpl ${it.id}")
                it.toPassedAssay()
            }
    }
}