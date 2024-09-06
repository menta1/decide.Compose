package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay14(
    answers: List<Answers>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        if (answer.answerValue.sum() == 2f) {
            points++
        }
    }

    return when {
        (points in 0f..3f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points in 4f..6f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

    }
}