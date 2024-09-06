package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay18(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var competition = 0f
    var adaptation = 0f
    var compromise = 0f
    var avoidance = 0f
    var cooperation = 0f

    answers.forEach { answer ->

        when (answer.answerValue) {
            1f -> {
                competition++
            }

            2f -> {
                adaptation++
            }

            3f -> {
                compromise++
            }

            4f -> {
                avoidance++
            }

            5f -> {
                cooperation++
            }
        }
    }

    val results = listOf(
        "competition" to competition,
        "adaptation" to adaptation,
        "compromise" to compromise,
        "avoidance" to avoidance,
        "cooperation" to cooperation,
    ).sortedByDescending { it.second }

    return when (results.first().first) {
        "competition" -> {
            getResultCompletedAssay("1", key)
        }

        "adaptation" -> {
            getResultCompletedAssay("2", key)
        }

        "compromise" -> {
            getResultCompletedAssay("3", key)
        }

        "avoidance" -> {
            getResultCompletedAssay("4", key)
        }

        else -> {
            getResultCompletedAssay("5", key)
        }
    }
}