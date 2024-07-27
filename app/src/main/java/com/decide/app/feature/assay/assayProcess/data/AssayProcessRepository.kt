package com.decide.app.feature.assay.assayProcess.data

import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource

interface AssayProcessRepository {
    suspend fun getAssay(id: Int): Resource<Assay>
    suspend fun saveResult(id: Int, answers:List<Answers> ): Resource<Boolean>
}