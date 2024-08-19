package com.decide.app.feature.assay.assayResult.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed class AssayWithResultState {

    @Immutable
    data object Default : AssayWithResultState()

    @Immutable
    data object Loading : AssayWithResultState()

    @Immutable
    data class Loaded(val result: String, val shortResult: String) : AssayWithResultState()

    @Immutable
    data object Error : AssayWithResultState()

    companion object {
        val Initial: AssayWithResultState = Default
    }
}