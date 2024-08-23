package com.decide.app.feature.assay.assayResult.ui

import androidx.compose.runtime.Immutable
import com.decide.app.feature.passed.models.ResultCompletedAssay

@Immutable
sealed class AssayWithResultState {

    @Immutable
    data object Loading : AssayWithResultState()

    @Immutable
    data class Loaded(val result: ResultCompletedAssay) : AssayWithResultState()

    @Immutable
    data object Error : AssayWithResultState()

    companion object {
        val Initial: AssayWithResultState = Loading
    }
}