package com.decide.app.feature.assay.assayProcess.ui.assayTimer

import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay

sealed interface AssayTimerState {
    data object Loading : AssayTimerState
    data object IsReady : AssayTimerState
    data class Loaded(val question: QuestionAssay, val progress: Float) : AssayTimerState
    data object Error : AssayTimerState
    data object TimeOver : AssayTimerState
    data class End(val idAssay: Int) : AssayTimerState
    companion object {
        val Initial: AssayTimerState = Loading
    }
}