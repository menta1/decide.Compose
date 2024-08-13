package com.decide.app.feature.passed.data

import android.util.Log
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.toPassedAssay
import com.decide.app.feature.passed.PassedAssay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PassedScreenRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
): PassedScreenRepository {
    override suspend fun fetchAllResults(): List<PassedAssay> {
        return localAssayStorage.assayDao().getAssaysWithNonEmptyResults().map {
            Log.d("TAG",it.name + it.results.size.toString())
            it.toPassedAssay() }

    }
}