package com.decide.app.feature.passed.ui.showPassedResult

import com.decide.app.feature.passed.models.ResultCompletedAssay

sealed interface ShowPassedResultState {
    data object Loading : ShowPassedResultState
    data class Success(
        val result: ResultCompletedAssay,
        val nameAssay: String
    ) : ShowPassedResultState

    data object Error : ShowPassedResultState
    companion object {
        val Initial: ShowPassedResultState = Loading
    }
}