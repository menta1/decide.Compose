package com.decide.app.feature.assay.assayResult.ui

import com.decide.app.feature.passed.models.ResultCompletedAssay

sealed class AssayWithResultState {

    data object Loading : AssayWithResultState()

    data class Loaded(val result: ResultCompletedAssay) : AssayWithResultState()

    data object Error : AssayWithResultState()

    companion object {
        val Initial: AssayWithResultState = Loading
    }
}