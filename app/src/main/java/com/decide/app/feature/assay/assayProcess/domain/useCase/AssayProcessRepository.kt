package com.decide.app.feature.assay.assayProcess.domain.useCase

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource

interface AssayProcessRepository {
    suspend fun getAssay(id: Int): Resource<Assay>
    suspend fun saveResult(id: Int, result: ResultCompletedAssay)
    suspend fun getKeys(id: Int): KeyAssay
}