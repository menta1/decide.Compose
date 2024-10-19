package com.decide.app.feature.assay.assayProcess.ui.assayText

import com.decide.app.activity.ShowAds


sealed interface EventAssayText {
    data class AssayText(
        val idQuestion: Int,
        val idAnswer: List<Int>,
        val answerValue: List<Float>
    ) : EventAssayText

    data class Ad(val ads: ShowAds) : EventAssayText
}

