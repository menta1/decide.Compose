package com.decide.app.feature.category.mainCategory.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.toCategory
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : CategoryRepository {

    override fun getCategories(): Flow<Resource<List<Category>, DecideException>> {
        return localStorage.categoryDao()::getFlowCategory.invoke()
            .map { result ->
                Resource.Success<List<Category>, DecideException>(result.map { it.toCategory() })
            }
            .catch {
                Resource.Error<List<Category>, DecideException>(
                    DecideException.NoFindElementDB(
                        it.message ?: "Нет такого элемента в БД",
                        "CategoryRepositoryImpl-getCategories"
                    )
                )
            }
    }
}