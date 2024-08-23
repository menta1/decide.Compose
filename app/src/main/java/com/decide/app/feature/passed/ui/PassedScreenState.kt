package com.decide.app.feature.passed.ui

import com.decide.app.feature.passed.models.PassedAssay

sealed interface PassedScreenState {

    data class Success(val assays: List<PassedAssay>) : PassedScreenState

    data object Empty : PassedScreenState
    data object Loading : PassedScreenState

    class Error(val message: String) : PassedScreenState

    companion object {
        val Initial: PassedScreenState = Loading
    }
}