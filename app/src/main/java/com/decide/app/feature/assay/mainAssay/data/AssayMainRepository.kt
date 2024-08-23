package com.decide.app.feature.assay.mainAssay.data

import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AssayMainRepository {
    fun getAssays(): Flow<Resource<List<Assay>>>
}