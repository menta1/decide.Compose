package com.decide.app.feature.assay.assayDescription.ui

sealed interface AssayDescriptionState {
    data object Loading : AssayDescriptionState
    data class Loaded(val description: String, val typeAssay: Int) : AssayDescriptionState
    data object Error : AssayDescriptionState
    companion object {
        val Initial: AssayDescriptionState = Loading
    }
}