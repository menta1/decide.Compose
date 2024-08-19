package com.decide.app.feature.assay.assayResult.data

import com.decide.app.database.local.dao.AssayDao
import com.decide.app.utils.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

class AssayWithResultRepositoryImpl @Inject constructor(
    private val assayDao: AssayDao
) : AssayWithResultRepository {
    override suspend fun getResult(id: Int, dateResult: Long): Resource<Pair<String, String>> {
        try {

            delay(500)
            val result = assayDao.getAssay(id).results
            val lastResult =
                if (dateResult != -1L) {
                    result.find { it.date == dateResult }
                } else result.last()
            return Resource.Success(
                Pair(
                    first = lastResult?.result ?: "",
                    second = lastResult?.shortResult ?: ""
                )
            )
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }
}