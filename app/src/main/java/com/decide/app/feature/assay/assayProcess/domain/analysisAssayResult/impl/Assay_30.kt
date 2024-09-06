package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay30(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var conflicts = 0f
    var relative = 0f
    var child = 0f
    var autonomy = 0f
    var expetation = 0f
    var behavior = 0f
    var domination = 0f
    var jealousy = 0f
    var money = 0f


    answers.forEach { answer ->
        conflicts += answer.answerValue
        when (answer.idAnswer) {
            1, 5, 8, 20 -> {
                relative += answer.answerValue
            }

            4, 11, 16, 23 -> {
                child += answer.answerValue
            }

            6, 18, 21, 22 -> {
                autonomy += answer.answerValue
            }

            2, 12, 27, 29 -> {
                expetation += answer.answerValue
            }

            3, 26, 28, 30 -> {
                behavior += answer.answerValue
            }

            9, 25, 31, 32 -> {
                domination += answer.answerValue
            }

            13, 14, 17, 24 -> {
                jealousy += answer.answerValue
            }

            7, 10, 15, 19 -> {
                money += answer.answerValue
            }
        }
    }

    val keysList = mutableListOf<String>()

    if (relative <= 0) {
        keysList.add("1")
    } else {
        keysList.add("2")
    }

    if (child <= 0) {
        keysList.add("3")
    } else {
        keysList.add("4")
    }

    if (autonomy <= 0) {
        keysList.add("5")
    } else {
        keysList.add("6")
    }

    if (expetation <= 0) {
        keysList.add("7")
    } else {
        keysList.add("8")
    }

    if (behavior <= 0) {
        keysList.add("9")
    } else {
        keysList.add("10")
    }

    if (domination <= 0) {
        keysList.add("11")
    } else {
        keysList.add("12")
    }

    if (jealousy <= 0) {
        keysList.add("13")
    } else {
        keysList.add("14")
    }

    if (money <= 0) {
        keysList.add("15")
    } else {
        keysList.add("16")
    }

    if (conflicts < -30f) {
        keysList.add("17")
    } else if (conflicts in -31f..31f) {
        keysList.add("18")
    } else {
        keysList.add("19")
    }

    return getResultCompletedAssay(keysList, key)
}