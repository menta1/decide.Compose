package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay19(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {
    var physicalAggression = 0f
    var indirectAggression = 0f
    var irritation = 0f
    var negativism = 0f
    var resentment = 0f
    var suspicion = 0f
    var verbalAggression = 0f
    var guilt = 0f


    answers.forEach { answer ->

        when (answer.answerValue) {
            1f -> {
                physicalAggression++
            }

            2f -> {
                indirectAggression++
            }

            3f -> {
                irritation++
            }

            4f -> {
                negativism++
            }

            5f -> {
                resentment++
            }

            6f -> {
                suspicion++
            }

            7f -> {
                verbalAggression++
            }

            else -> {
                guilt++
            }
        }
    }

    val agressive = verbalAggression + irritation + physicalAggression
    val hostility = resentment + suspicion

    val shortResult = mutableListOf<String>()
    val result = mutableListOf<String>()
    val keyResults = mutableListOf<String>()

    if (agressive in 0f..25f) {
        shortResult.add("1")
        result.add("1")
        keyResults.add("1")
    } else {
        shortResult.add("2")
        result.add("2")
        keyResults.add("2")
    }
    if (hostility in 0f..10f) {
        shortResult.add("3")
        result.add("3")
        keyResults.add("3")
    } else {
        shortResult.add("4")
        result.add("4")
        keyResults.add("4")
    }

    if (negativism in 0f..3f) {
        shortResult.add("7")
        result.add("7")
        keyResults.add("7")
    } else {
        shortResult.add("8")
        result.add("8")
        keyResults.add("8")
    }

    if (guilt in 0f..5f) {
        shortResult.add("5")
        result.add("5")
        keyResults.add("5")
    } else {
        shortResult.add("6")
        result.add("6")
        keyResults.add("6")
    }


    return getResultCompletedAssay(keyResults, key)
}