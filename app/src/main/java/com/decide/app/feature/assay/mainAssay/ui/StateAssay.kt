package com.decide.app.feature.assay.mainAssay.ui

import kotlinx.collections.immutable.ImmutableList


sealed class StateAssay {
    data object None : StateAssay()
    class Error(val message: String) : StateAssay()
    class Success(val assays: ImmutableList<AssayUI>) : StateAssay()
}