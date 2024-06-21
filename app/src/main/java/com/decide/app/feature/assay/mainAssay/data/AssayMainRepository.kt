package com.decide.app.feature.assay.mainAssay.data

import com.decide.app.feature.assay.mainAssay.modals.Assay

interface AssayMainRepository {
    suspend fun getAssays(
        onSuccess: (List<Assay>) -> Unit,
        onError: (message: String) -> Unit,
    )
}