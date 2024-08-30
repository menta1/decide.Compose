package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay1(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var points = 0f
    answers.forEach {
        points += it.answerValue
    }
    val result: Float = points / 20

    return when {
        (result in 0f..1f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (result in 1f..2f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        (result in 2f..3f) -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }

        (result in 3f..3.4f) -> {
            getResultCompletedAssay(key = "4", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "5", keyAssay = key)
        }
    }
}