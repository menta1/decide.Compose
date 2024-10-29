package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay6(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f
    val multiplierStats = 0.8
    answers.forEach {
        points += it.answerValue
    }

    return when {
        (points < 50f) -> {
            getResultCompletedAssay(
                key = "1",
                keyAssay = key,
                resultForStatistic = points.toDouble() * multiplierStats
            )
        }

        (points in 50f..59f) -> {
            getResultCompletedAssay(
                key = "2",
                keyAssay = key,
                resultForStatistic = points.toDouble() * multiplierStats
            )
        }

        (points in 60f..69f) -> {
            getResultCompletedAssay(
                key = "3",
                keyAssay = key,
                resultForStatistic = points.toDouble() * multiplierStats
            )
        }

        else -> {
            getResultCompletedAssay(
                key = "4",
                keyAssay = key,
                resultForStatistic = points.toDouble() * multiplierStats
            )
        }
    }
}