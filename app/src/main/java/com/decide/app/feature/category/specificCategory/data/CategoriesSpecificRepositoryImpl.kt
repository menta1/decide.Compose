package com.decide.app.feature.category.specificCategory.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.toAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CategoriesSpecificRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : CategoriesSpecificRepository {
    override suspend fun fetchAssaysByIdCategory(idCategory: Int): List<Assay> {
        return localAssayStorage.assayDao().fetchAllAssaysByIdCategory(idCategory)
            .map { it.toAssay() }
    }
}