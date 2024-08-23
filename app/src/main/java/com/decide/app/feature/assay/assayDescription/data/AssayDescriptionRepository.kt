package com.decide.app.feature.assay.assayDescription.ui.data

import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource

interface AssayDescriptionRepository {
    // fun getTemp(): Assay
    suspend fun getAssay(id: Int): Resource<Assay>
}