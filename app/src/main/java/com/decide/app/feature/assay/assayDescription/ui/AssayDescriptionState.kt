package com.decide.app.feature.assay.assayDescription.ui

sealed interface AssayDescriptionState {
    data object Loading : AssayDescriptionState
    data class Success(val data: String) : AssayDescriptionState
    data object Error : AssayDescriptionState
    companion object {
        val Initial: AssayDescriptionState = Loading
    }
}