package com.decide.app.feature.assay.assayDescription.ui

import com.decide.app.feature.assay.mainAssay.modals.Assay

sealed class AssayDescriptionState {
    data object Default : AssayDescriptionState()
    data class Success(val data: Assay) : AssayDescriptionState()
    data object Error : AssayDescriptionState()
}