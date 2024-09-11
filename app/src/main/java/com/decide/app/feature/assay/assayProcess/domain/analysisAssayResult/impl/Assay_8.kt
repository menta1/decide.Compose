package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay
import timber.log.Timber

internal fun assay8(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points1 = 0f
    var points2 = 0f

    answers.forEach {
        Timber.tag("TAG").d("points1 = $points1")
        Timber.tag("TAG").d("points2 = $points2")
        when {
            (it.idQuestion in 1..2) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 3..4) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 5) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 6..7) -> {
                points1 += it.answerValue
            }

            (it.idQuestion == 8) -> {
                points2 += it.answerValue
            }

            (it.idQuestion == 9) -> {
                points1 += it.answerValue
            }

            (it.idQuestion in 10..11) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 12..14) -> {
                points1 += it.answerValue
            }

            (it.idQuestion in 15..16) -> {
                points2 += it.answerValue
            }

            (it.idQuestion in 17..18) -> {
                points1 += it.answerValue
            }

            (it.idQuestion in 19..20) -> {
                points2 += it.answerValue
            }
        }
    }

    val result = points1 - points2 + 35f
    val forStatistics = 0.6*(result-5)
    Timber.tag("TAG").d("assay 8 = $result")
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