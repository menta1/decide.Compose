package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay15(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        points += answer.answerValue
    }

    return when {
        (points in 0f..14f) -> {
            getResultCompletedAssay(key = "4", keyAssay = key, resultForStatistic = 0.5 * points)
        }

        (points in 15f..24f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key, resultForStatistic = 0.5 * points)
        }

        (points in 25f..40f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key, resultForStatistic = 0.5 * points)
        }

        else -> {
            getResultCompletedAssay(key = "1", keyAssay = key, resultForStatistic = 0.5 * points)
        }

    }
}