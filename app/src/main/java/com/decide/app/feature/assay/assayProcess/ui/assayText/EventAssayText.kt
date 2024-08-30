package com.decide.app.feature.assay.assayProcess.ui.assayText

data class EventAssayText(
    val idQuestion: Int,
    val idAnswer: List<Int>,
    val answerValue: List<Float>
)
