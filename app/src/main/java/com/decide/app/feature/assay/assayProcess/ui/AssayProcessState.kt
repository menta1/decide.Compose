package com.decide.app.feature.assay.assayProcess.ui

import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay

sealed interface AssayProcessState {
    data object Loading : AssayProcessState
    data class AssayWithText(val question: QuestionAssay, val progress: Float) : AssayProcessState
    data class AssayWithImage(val question: QuestionAssay) : AssayProcessState
    data class AssayWithTimer(val question: QuestionAssay) : AssayProcessState
    data object Error : AssayProcessState
    data class End(val idAssay: Int) : AssayProcessState
    companion object {
        val Initial: AssayProcessState = Loading
    }
}