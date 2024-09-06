package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay9(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points1 = 0f
    var points2 = 0f

    answers.forEach {
        when {

            (it.idAnswer == 1) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 2..5) -> {
                points1 += it.answerValue
            }

            (it.idAnswer in 6..7) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 8..9) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 10) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 11..12) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 13) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 14..15) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 16) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 17..18) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 19) -> {
                points2 += it.answerValue
            }

            (it.idAnswer == 20) -> {
                points1 += it.answerValue
            }
        }
    }

    points1 - points2 + 35f

    return when {
        (points1 <= 30f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key)
        }

        (points1 in 31f..45f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key)
        }

        else -> {
            getResultCompletedAssay(key = "3", keyAssay = key)
        }
    }
}
