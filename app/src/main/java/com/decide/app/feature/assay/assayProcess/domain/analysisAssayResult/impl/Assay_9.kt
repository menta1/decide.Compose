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

            (it.idQuestion == 1) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 2..5) -> {
                points1 += it.answerValue
            }

            (it.idQuestion in 6..7) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 8..9) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 10) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 11..12) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 13) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 14..15) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 16) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 17..18) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 19) -> {
                points2 += it.answerValue
            }

            (it.idQuestion == 20) -> {
                points1 += it.answerValue
            }
        }
    }

    val result = points1 - points2 + 35f
    val forStatistics = 0.6 * (result - 20)

    return when {
        (result <= 30f) -> {
            getResultCompletedAssay(key = "1", keyAssay = key, resultForStatistic = forStatistics)
        }

        (result in 31f..45f) -> {
            getResultCompletedAssay(key = "2", keyAssay = key, resultForStatistic = forStatistics)
        }

        else -> {
            getResultCompletedAssay(key = "3", keyAssay = key, resultForStatistic = forStatistics)
        }
    }
}
