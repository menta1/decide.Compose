package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay8(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var points1 = 0f
    var points2 = 0f

    answers.forEach {
        when {
            (it.idAnswer in 1..2) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 3..4) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 5) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 6..7) -> {
                points1 += it.answerValue
            }

            (it.idAnswer == 8) -> {
                points2 += it.answerValue
            }

            (it.idAnswer == 9) -> {
                points1 += it.answerValue
            }

            (it.idAnswer in 10..11) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 12..14) -> {
                points1 += it.answerValue
            }

            (it.idAnswer in 15..16) -> {
                points2 += it.answerValue
            }

            (it.idAnswer in 17..18) -> {
                points1 += it.answerValue
            }

            (it.idAnswer in 19..20) -> {
                points2 += it.answerValue
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