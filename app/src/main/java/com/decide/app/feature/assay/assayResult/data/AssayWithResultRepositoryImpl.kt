package com.decide.app.feature.assay.assayResult.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.assay.toAssay
import com.decide.app.database.local.entities.assay.toResultCompletedAssay
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AssayWithResultRepositoryImpl @Inject constructor(
    private val localStorage: AppDatabase
) : AssayWithResultRepository {

    override suspend fun getResult(id: Int): Flow<Resource<ResultCompletedAssay, Exception>> {
        return localStorage.assayDao().getResultAssay(id).map {
            Resource.Success<ResultCompletedAssay, Exception>(
                it.results.last().toResultCompletedAssay()
            )
        }
            .catch { Resource.Error<ResultCompletedAssay, Exception>(Exception("Нет такого элемента в БД")) }
    }

    override suspend fun getResult(
        id: Int,
        dateResult: Long
    ): Flow<Resource<ResultCompletedAssay, Exception>> {
        return localStorage.assayDao().getResultAssay(id).map { assay ->
            Resource.Success<ResultCompletedAssay, Exception>(
                assay.results.find { it.date == dateResult }?.toResultCompletedAssay()
                    ?: throw Exception()
            )
        }.catch {
            Resource.Error<ResultCompletedAssay, Exception>(
                DecideException.NoFindElementDB(
                    it.message ?: "Нет такого элемента в БД",
                    "AssayWithResultRepositoryImpl getResult"
                )
            )
        }
    }

    override suspend fun getAssay(id: Int): Assay {
        return localStorage.assayDao().getAssay(id).toAssay()
    }

    override suspend fun setRating(
        id: Int,
        star: String
    ) {
        localStorage.assayDao().setRating(id = id, star = star)
    }
}