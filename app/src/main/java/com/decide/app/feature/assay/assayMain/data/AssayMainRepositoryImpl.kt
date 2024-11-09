package com.decide.app.feature.assay.assayMain.data

import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.entities.assay.toAssay
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.DecideException
import com.decide.app.utils.NetworkChecker
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AssayMainRepositoryImpl @Inject constructor(
    private val localStorageAssay: AssayDao,
    private val networkChecker: NetworkChecker,
) : AssayMainRepository {

    override suspend fun getAssays(): Resource<Flow<List<Assay>>, DecideException> {
        return if (networkChecker.isConnected()) {
            Resource.Success(localStorageAssay.getFlowAllAssays().map { entities ->
                entities.map { it.toAssay() }.shuffled()
            })
        } else {
            Resource.Error(DecideException.NoInternet())
        }
    }
}