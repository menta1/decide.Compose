package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay16(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var points = 0f
    var pointsLie = 0f

    answers.forEach { answer ->

        when (answer.idAnswer) {
            16, 20, 27, 29, 41, 51, 59, 2, 10, 55 -> {
                pointsLie++
            }

            else -> {
                points += answer.answerValue
            }
        }
    }

    val listKey: MutableList<String> = mutableListOf()

    when {
        (points in 0f..14f) -> {
            listKey.add("4")
        }

        (points in 15f..24f) -> {
            listKey.add("3")
        }

        (points in 25f..40f) -> {
            listKey.add("2")
        }

        else -> {
            listKey.add("1")
        }
    }
    if (pointsLie >= 5) {
        listKey.add("5")
    } else {
        listKey.add("6")
    }
    return getResultCompletedAssay(listKey, key)
}