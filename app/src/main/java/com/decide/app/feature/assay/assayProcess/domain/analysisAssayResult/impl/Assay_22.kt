package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay22(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var lie = 0f
    var points = 0f

    answers.forEach { answer ->

        when (answer.idAnswer) {
            2, 5, 8, 9, 10, 12, 13, 15, 16, 19, 21, 22, 24, 25, 26, 27, 29, 32 -> {
                points += answer.answerValue
            }
        }

        when (answer.idAnswer) {
            3, 9, 11, 13, 28, 36 -> {
                if (answer.answerValue == 0f) {
                    lie++
                }
                if (answer.idAnswer == 11) {
                    if (answer.answerValue == 5f) {
                        lie++
                    }
                }
                if (answer.idAnswer == 13) {
                    if (answer.answerValue == 5f) {
                        lie++
                    }
                }
            }

            15, 27 -> {
                if (answer.answerValue == 5f) {
                    lie++
                }
            }

        }
    }

    val keysList = mutableListOf<String>()

    if (lie > 5) {
        return getResultCompletedAssay("1", key)
    }
    if (lie in 4f..5f) {
        keysList.add("2")
    }

    when {

        (points in 82f..90f) -> {
            keysList.add("3")
        }

        (points in 63f..81f) -> {
            keysList.add("4")
        }

        (points in 37f..62f) -> {
            keysList.add("5")
        }

        (points in 12f..36f) -> {
            keysList.add("6")
        }

        (points <= 35f) -> {
            keysList.add("7")
        }

    }

    return getResultCompletedAssay(keysList, key)
}