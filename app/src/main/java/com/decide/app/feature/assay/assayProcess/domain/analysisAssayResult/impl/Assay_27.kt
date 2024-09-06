package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay27(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var repertoire = 0f
    var sensitivity = 0f
    var control = 0f

    answers.forEach { answer ->
        when (answer.idAnswer) {
            1, 5, 8, 12, 15, 17 -> {
                repertoire += answer.answerValue
            }

            2, 4, 7, 11, 14, 18, 20 -> {
                sensitivity += answer.answerValue
            }

            3, 6, 9, 10, 13, 16, 19 -> {
                control += answer.answerValue
            }
        }
    }

    val keysList = mutableListOf<String>()

    when {
        (repertoire in 0f..7f) -> {
            keysList.add("1")
        }

        (repertoire in 8f..15f) -> {
            keysList.add("2")
        }

        (repertoire > 15f) -> {
            keysList.add("3")
        }
    }
    when {
        (sensitivity in 0f..9f) -> {
            keysList.add("4")
        }

        (sensitivity in 10f..19f) -> {
            keysList.add("5")
        }

        (sensitivity > 20f) -> {
            keysList.add("6")
        }
    }
    when {
        (repertoire in 0f..9f) -> {
            keysList.add("7")
        }

        (repertoire in 10f..19f) -> {
            keysList.add("8")
        }

        (repertoire > 20f) -> {
            keysList.add("9")
        }
    }

    return getResultCompletedAssay(keysList, key)
}