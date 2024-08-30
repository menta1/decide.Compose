package com.decide.app.feature.assay.assayProcess.ui.assayTimer

sealed class EventAssayTimer {
    data class selectedAnswer(
        val idQuestion: Int,
        val idAnswer: List<Int>,
        val answerValue: List<Float>
    ) : EventAssayTimer()

    data object StartTimer : EventAssayTimer()
}