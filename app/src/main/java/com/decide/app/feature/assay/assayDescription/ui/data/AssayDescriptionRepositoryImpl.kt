package com.decide.app.feature.assay.assayDescription.ui.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.toAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AssayDescriptionRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase
) : AssayDescriptionRepository {

    override suspend fun getAssay(id: Int): Resource<Assay> {
        val result = localAssayStorage.assayDao().getAssay(id)
        return if (result != null) {
            Resource.Success(result.toAssay())
        } else {
            Resource.Error(Exception("Ничего не найдено"))
        }
    }
}