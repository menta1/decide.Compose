package com.decide.app.feature.assay.mainAssay.ui

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

sealed class StateAssay {
    data object Loading : StateAssay()
    class Error(val message: String) : StateAssay()
    @Immutable
    class Loaded(val assays: ImmutableList<AssayUI>) : StateAssay()
    companion object {
        val Initial: StateAssay = Loading
    }
}