package com.decide.app.feature.passed.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.toPassedAssay
import com.decide.app.feature.passed.models.PassedAssay
import javax.inject.Inject

class PassedScreenRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase
) : PassedScreenRepository {
    override suspend fun fetchAllResults(): List<PassedAssay> {
        return localAssayStorage.assayDao().getAssaysWithNonEmptyResults().map {//надо наверное брать с профиля
            it.toPassedAssay()
        }
    }
}