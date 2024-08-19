package com.decide.app.feature.passed.ui

import com.decide.app.feature.passed.models.PassedAssay

sealed class PassedScreenState {

    data class Success(val assays: List<PassedAssay>) : PassedScreenState()

    data object Default : PassedScreenState()

    class Error(val message: String) : PassedScreenState()

    companion object {
        val Initial: PassedScreenState = Default
    }
}