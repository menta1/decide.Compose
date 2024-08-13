package com.decide.app.feature.passed.ui

import com.decide.app.feature.passed.PassedAssay
import com.decide.app.navigation.Assay

sealed class PassedScreenState {

    data class Success(val assays: List<PassedAssay>) : PassedScreenState()

    data object Default : PassedScreenState()

    class Error(val message: String) : PassedScreenState()

    companion object {
        val Initial: PassedScreenState = Default
    }
}