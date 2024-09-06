package com.decide.app.feature.assay.assayResult.data

import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AssayWithResultRepository {
    suspend fun getResult(id: Int): Flow<Resource<ResultCompletedAssay, Exception>>
    suspend fun getResult(
        id: Int,
        dateResult: Long
    ): Flow<Resource<ResultCompletedAssay, Exception>>
}