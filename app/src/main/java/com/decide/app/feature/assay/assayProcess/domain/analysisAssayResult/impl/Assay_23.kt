package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay23(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        points += answer.answerValue
    }

    return when {
        (points > 30f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points in 25f..29f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        (points in 19f..24f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

        (points in 14f..18f) -> {
            getResultCompletedAssay(key = "4", keyAssay = key)
        }

        (points in 9f..13f) -> {
            getResultCompletedAssay(key = "5", keyAssay = key)
        }

        (points in 4f..8f) -> {
            getResultCompletedAssay(key = "6", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "7", keyAssay = key)
        }

    }
}