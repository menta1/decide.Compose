package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay28(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var acceptance = 0f
    var cooperation = 0f
    var symbiosis = 0f
    var hypersoc = 0f
    var loser = 0f

    answers.forEach { answer ->
        when (answer.idAnswer) {
            3, 4, 8, 10, 12, 14, 15, 16, 18, 20, 24, 26, 27, 29, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 49, 52, 53, 55, 56, 60 -> {
                acceptance += answer.answerValue
            }

            6, 9, 21, 25, 31, 34, 35, 36 -> {
                cooperation += answer.answerValue
                if (answer.idAnswer == 9) loser += answer.answerValue
            }

            1, 5, 7, 28, 32, 41, 58 -> {
                symbiosis += answer.answerValue
                if (answer.idAnswer == 28) loser += answer.answerValue
            }

            2, 19, 30, 48, 50, 57, 59 -> {
                hypersoc += answer.answerValue
            }

            11, 13, 17, 22, 54, 61 -> {
                loser += answer.answerValue
            }
        }
    }

    val keysList = mutableListOf<String>()

    when {
        (acceptance > 20f) -> {
            keysList.add("1")
        }

        (acceptance in 10f..19f) -> {
            keysList.add("2")
        }

        (acceptance <= 9f) -> {
            keysList.add("3")
        }
    }
    when {
        (cooperation > 6f) -> {
            keysList.add("4")
        }

        (cooperation in 3f..5f) -> {
            keysList.add("5")
        }

        (cooperation <= 2f) -> {
            keysList.add("6")
        }
    }
    when {
        (symbiosis > 6f) -> {
            keysList.add("7")
        }

        (symbiosis in 3f..5f) -> {
            keysList.add("8")
        }

        (symbiosis <= 2f) -> {
            keysList.add("9")
        }
    }
    when {
        (hypersoc > 6f) -> {
            keysList.add("10")
        }

        (hypersoc in 3f..5f) -> {
            keysList.add("11")
        }

        (hypersoc <= 2f) -> {
            keysList.add("12")
        }
    }
    when {
        (loser > 7f) -> {
            keysList.add("13")
        }

        (loser in 4f..6f) -> {
            keysList.add("14")
        }

        (loser <= 3f) -> {
            keysList.add("15")
        }
    }

    return getResultCompletedAssay(keysList, key)
}