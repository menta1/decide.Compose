package com.decide.app.feature.category.mainCategory.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.toCategory
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> {
        coroutineScope.launch { remoteAssayStorage.getCategories() }

        return localStorage.categoryDao()::getFlowCategory.invoke()
            .map { result ->
                Resource.Success(result.map { it.toCategory() })
            }
            .catch { Resource.Error(it) }
    }
}