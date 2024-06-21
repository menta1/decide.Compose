package com.decide.app.database.remote.assay

import com.decide.app.database.remote.assay.dto.AssayDTO
import com.decide.app.database.remote.assay.dto.CategoryDTO
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteAssayStorage {
    suspend fun getAssay(id: String): Flow<Result<AssayDTO>>
    suspend fun getAssays(onResult: (Resource<List<AssayDTO>>) -> Unit)
    fun getCategories(): Flow<Result<List<CategoryDTO>>>

}