package com.decide.app.feature.assay.assayResult.data

import com.decide.app.utils.Resource

interface AssayWithResultRepository {
    suspend fun getResult(id: Int, dateResult: Long): Resource<Pair<String, String>>
}