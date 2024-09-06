package com.decide.app.feature.assay.assayMain.data

import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AssayMainRepository {
    fun getAssays(): Flow<Resource<List<Assay>, DecideException>>
}