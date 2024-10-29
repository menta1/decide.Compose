package com.decide.app.feature.category.mainCategory.data

import com.decide.app.database.local.dao.CategoryDao
import com.decide.app.database.local.entities.toCategory
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localStorageCategory: CategoryDao,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : CategoryRepository {

    override suspend fun getCategories(): Resource<List<Category>, DecideException> {

        try {
            val categoryList = localStorageCategory.getAllCategory()
            return Resource.Success(categoryList.map { it.toCategory() })
        } catch (e: Exception) {
            return Resource.Error(
                DecideException.NoFindElementDB(
                    e.message ?: "Нет такого элемента в БД", "CategoryRepositoryImpl-getCategories"
                )
            )
        }
    }
}