package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay12(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var points1 = 0f
    var points2 = 0f
    var points3 = 0f
    var points4 = 0f
    var points5 = 0f

    answers.forEach {
        when {
            (it.answerValue == 1f) -> {
                points1++
            }

            (it.answerValue == 2f) -> {
                points2++
            }

            (it.answerValue == 3f) -> {
                points3++
            }

            (it.answerValue == 4f) -> {
                points4++
            }

            (it.answerValue == 5f) -> {
                points5++
            }
        }
    }
    val maxPoints = maxOf(points1, points2, points3, points4, points5)
    return if (points1 == maxPoints) {
        getResultCompletedAssay(key = "1", keyAssay = key)
    } else if (points2 == maxPoints) {
        getResultCompletedAssay(key = "1", keyAssay = key)
    } else if (points3 == maxPoints) {
        getResultCompletedAssay(key = "1", keyAssay = key)
    } else if (points4 == maxPoints) {
        getResultCompletedAssay(key = "1", keyAssay = key)
    } else {
        getResultCompletedAssay(key = "1", keyAssay = key)
    }
}