package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay29(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        points += answer.answerValue
    }

    return when {

        (points < 16f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points in 17f..22f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        (points in 23f..26f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

        (points in 27f..28f) -> {
            getResultCompletedAssay(key = "4", keyAssay = key)
        }

        (points in 29f..32f) -> {
            getResultCompletedAssay(key = "5", keyAssay = key)
        }

        (points in 33f..38f) -> {
            getResultCompletedAssay(key = "6", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "7", keyAssay = key)
        }

    }
}