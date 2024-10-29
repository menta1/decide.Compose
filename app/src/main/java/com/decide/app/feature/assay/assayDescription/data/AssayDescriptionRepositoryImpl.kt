package com.decide.app.feature.assay.assayDescription.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.toAssay
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AssayDescriptionRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase
) : AssayDescriptionRepository {

    override suspend fun getAssay(id: Int): Resource<Assay, Exception> {
        val result = localAssayStorage.assayDao().getAssay(id).toAssay()
        return Resource.Success(result)
    }
}