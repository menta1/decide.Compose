package com.decide.app.feature.assay.assayResult.data

import com.decide.app.database.local.dao.AssayDao
import com.decide.app.utils.Resource
import javax.inject.Inject

class AssayWithResultRepositoryImpl @Inject constructor(
    private val assayDao: AssayDao
): AssayWithResultRepository {
    override suspend fun getResult(id: Int): Resource<String> {
        try {
            val result = assayDao.getAssay(id).results.last()
            return Resource.Success(result.result)
        }catch (e: Exception){
            return Resource.Error(e)
        }
    }
}