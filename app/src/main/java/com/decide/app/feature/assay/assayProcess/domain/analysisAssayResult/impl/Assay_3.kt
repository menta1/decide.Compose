package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay3(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {

    var points = 0f
    answers.forEach {
        points += it.answerValue
    }
    val result = points

    return when {
        (result <= 68) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }
    }
}