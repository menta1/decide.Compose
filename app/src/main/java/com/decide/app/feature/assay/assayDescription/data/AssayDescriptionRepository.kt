package com.decide.app.feature.assay.assayDescription.data

import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.Resource

interface AssayDescriptionRepository {
    suspend fun getAssay(id: Int): Resource<Assay, Exception>
}