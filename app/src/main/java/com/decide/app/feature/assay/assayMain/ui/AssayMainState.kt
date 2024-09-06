package com.decide.app.feature.assay.assayMain.ui

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

sealed class AssayMainState {
    data object Loading : AssayMainState()
    data object Empty : AssayMainState()
    class Error(val message: String) : AssayMainState()

    @Immutable
    class Loaded(val assays: ImmutableList<AssayUI>) : AssayMainState()

    companion object {
        val Initial: AssayMainState = Loading
    }
}