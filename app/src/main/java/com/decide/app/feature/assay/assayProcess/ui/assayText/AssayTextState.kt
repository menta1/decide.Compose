package com.decide.app.feature.assay.assayProcess.ui.assayText

import com.decide.app.feature.assay.assayMain.modals.QuestionAssay

sealed interface AssayTextState {
    data object Loading : AssayTextState
    data class Loaded(
        val question: QuestionAssay,
        val progress: Float
    ) : AssayTextState

    data object Error : AssayTextState
    data class End(val idAssay: Int) : AssayTextState
    companion object {
        val Initial: AssayTextState = Loading
    }
}