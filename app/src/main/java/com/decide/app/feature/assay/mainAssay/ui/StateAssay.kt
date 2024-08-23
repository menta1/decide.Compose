package com.decide.app.feature.assay.mainAssay.ui

import kotlinx.collections.immutable.ImmutableList

sealed class StateAssay {
    data object Loading : StateAssay()
    class Error(val message: String) : StateAssay()
    class Loaded(val assays: ImmutableList<AssayUI>) : StateAssay()
    companion object {
        val Initial: StateAssay = Loading
    }
}