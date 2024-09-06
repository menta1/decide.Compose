package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay25(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        points += answer.answerValue
    }

    return when {

        (points > 17f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points in 10f..16f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        (points in 4f..9f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "4", keyAssay = key)
        }

    }
}