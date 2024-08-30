package com.decide.app.database.remote.assay

import com.decide.app.database.remote.dto.AssayDTO
import kotlinx.coroutines.flow.Flow

interface RemoteAssayStorage {
    suspend fun getAssay(id: String): Flow<Result<AssayDTO>>
    suspend fun getAssays()
    suspend fun getCategories()
    suspend fun getKey(id: String)
}