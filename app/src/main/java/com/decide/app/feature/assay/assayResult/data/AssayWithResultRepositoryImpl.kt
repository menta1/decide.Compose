package com.decide.app.feature.assay.assayResult.data

import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dto.toResultCompletedAssay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AssayWithResultRepositoryImpl @Inject constructor(
    private val localStorage: AssayDao
) : AssayWithResultRepository {

    override suspend fun getResult(id: Int): Flow<Resource<ResultCompletedAssay>> {
        //delay(2000)
        return localStorage.getResultAssay(id).map {
            Resource.Success(it.results.last().toResultCompletedAssay())
        }.catch { Resource.Error(Exception("Нет такого элемента в БД")) }
    }

    override suspend fun getResult(
        id: Int,
        dateResult: Long
    ): Flow<Resource<ResultCompletedAssay>> {
        // delay(2000)
        return localStorage.getResultAssay(id).map { assay ->
            Resource.Success(
                assay.results.find { it.date == dateResult }?.toResultCompletedAssay()
                    ?: throw Exception()
            )
        }.catch { Resource.Error(Exception("Нет такого элемента в БД")) }
    }
}