package com.decide.app.feature.assay.assayResult.data

import com.decide.app.database.local.dao.AssayDao
import com.decide.app.utils.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

class AssayWithResultRepositoryImpl @Inject constructor(
    private val assayDao: AssayDao
) : AssayWithResultRepository {
    override suspend fun getResult(id: Int): Resource<String> {
        try {
            delay(5000)
            val result = assayDao.getAssay(id).results
            val res = result.last()
            return Resource.Success(res.result)
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }
}