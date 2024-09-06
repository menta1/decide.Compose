package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay20(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var points = 0f
    var destructiveAggress = 0f
    var suppressionAggress = 0f

    answers.forEach { answer ->

        points += answer.answerValue
        when (answer.answerValue) {
            1f -> {
                suppressionAggress++
            }

            3f -> {
                destructiveAggress++
            }

        }
    }

    val keysList = mutableListOf<String>()

    when {

        (points >= 45f) -> {
            keysList.add("1")
        }

        (points in 36f..44f) -> {
            keysList.add("2")
        }

        (points <= 35f) -> {
            keysList.add("3")
        }

    }
    if (destructiveAggress >= 7f && suppressionAggress <= 7f) {
        keysList.add("4")
    }
    if (destructiveAggress <= 7f && suppressionAggress >= 7f) {
        keysList.add("5")
    }
    return getResultCompletedAssay(keysList, key)
}