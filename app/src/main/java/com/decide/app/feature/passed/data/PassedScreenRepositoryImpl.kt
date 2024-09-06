package com.decide.app.feature.passed.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.toPassedAssay
import com.decide.app.feature.passed.models.PassedAssay
import javax.inject.Inject

class PassedScreenRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase
) : PassedScreenRepository {
    override suspend fun fetchAllResults(): List<PassedAssay> {
        return localAssayStorage.assayDao().getAssaysWithNonEmptyResults().map {
            it.toPassedAssay()
        }
    }
}