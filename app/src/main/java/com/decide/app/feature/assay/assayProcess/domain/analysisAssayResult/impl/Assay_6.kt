package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay6(answers: List<Answers>, key: KeyAssay): ResultCompletedAssay {
    var points = 0f
    answers.forEach {
        points += it.answerValue
    }

    return when {
        (points < 50f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points in 50f..59f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        (points in 60f..69f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "4", keyAssay = key)
        }
    }
}