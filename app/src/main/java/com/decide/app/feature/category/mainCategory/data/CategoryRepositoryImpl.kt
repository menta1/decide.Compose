package com.decide.app.feature.category.mainCategory.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.dto.toCategory
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor (
    private val remoteAssayStorage: RemoteAssayStorage,
    private val localAssayStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
): CategoryRepository {

    override suspend fun getCategories(
        onSuccess: (List<Category>) -> Unit,
        onError: (message: String) -> Unit
    ) {
        remoteAssayStorage.getCategories(){
            when(it){
                is Resource.Error ->  onError(it.exception.toString())
                is Resource.Success -> {

                    onSuccess(it.data.map { categoryDTO ->
                        categoryDTO.toCategory()
                    })
                }
            }
        }
    }
}