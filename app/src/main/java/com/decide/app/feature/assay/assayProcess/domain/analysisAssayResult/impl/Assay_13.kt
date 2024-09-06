package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import android.util.Log
import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay13(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {

    var points1 = 0f
    var points2 = 0f
    var points3 = 0f
    var points4 = 0f
    var points5 = 0f
    var points6 = 0f
    var points7 = 0f
    var points8 = 0f
    var points9 = 0f
    var points10 = 0f
    var points11 = 0f
    var points12 = 0f
    var points13 = 0f
    var points14 = 0f
    var points15 = 0f
    var points16 = 0f
    var points17 = 0f
    var points18 = 0f
    var points19 = 0f
    var points20 = 0f
    var points21 = 0f
    var points22 = 0f
    var points23 = 0f
    var points24 = 0f
    var anotherAnswer = 0f

    answers.forEach {
        when {
            (it.answerValue == 1f) -> {
                points1++
            }

            (it.answerValue == 2f) -> {
                points2++
            }

            (it.answerValue == 3f) -> {
                points3++
            }

            (it.answerValue == 4f) -> {
                points4++
            }

            (it.answerValue == 5f) -> {
                points5++
            }

            (it.answerValue == 6f) -> {
                points6++
            }

            (it.answerValue == 7f) -> {
                points7++
            }

            (it.answerValue == 8f) -> {
                points8++
            }

            (it.answerValue == 9f) -> {
                points9++
            }

            (it.answerValue == 10f) -> {
                points10++
            }

            (it.answerValue == 11f) -> {
                points11++
            }

            (it.answerValue == 12f) -> {
                points12++
            }

            (it.answerValue == 13f) -> {
                points13++
            }

            (it.answerValue == 14f) -> {
                points14++
            }

            (it.answerValue == 15f) -> {
                points15++
            }

            (it.answerValue == 16f) -> {
                points16++
            }

            (it.answerValue == 17f) -> {
                points17++
            }

            (it.answerValue == 18f) -> {
                points18++
            }

            (it.answerValue == 19f) -> {
                points19++
            }

            (it.answerValue == 20f) -> {
                points20++
            }

            (it.answerValue == 21f) -> {
                points21++
            }

            (it.answerValue == 22f) -> {
                points22++
            }

            (it.answerValue == 23f) -> {
                points23++
            }

            (it.answerValue == 24f) -> {
                points24++
            }

            else -> {
                anotherAnswer++
            }
        }
    }
    val maxPoints = listOf(
        "points1" to points1,
        "points2" to points2,
        "points3" to points3,
        "points4" to points4,
        "points5" to points5,
        "points6" to points6,
        "points7" to points7,
        "points8" to points8,
        "points9" to points9,
        "points10" to points10,
        "points11" to points11,
        "points12" to points12,
        "points13" to points13,
        "points14" to points14,
        "points15" to points15,
        "points16" to points16,
        "points17" to points17,
        "points18" to points18,
        "points19" to points19,
        "points20" to points20,
        "points21" to points21,
        "points22" to points22,
        "points23" to points23,
        "points24" to points24
    ).sortedByDescending { it.second }.take(3)

    val results = mutableListOf<String>()

    maxPoints.forEach {
        when {
            (it.first == "points1") -> {
                results.add("1")
            }

            (it.first == "points2") -> {
                results.add("2")
            }

            (it.first == "points3") -> {
                results.add("3")
            }

            (it.first == "points4") -> {
                results.add("4")
            }

            (it.first == "points5") -> {
                results.add("5")
            }

            (it.first == "points6") -> {
                results.add("6")
            }

            (it.first == "points7") -> {
                results.add("7")
            }

            (it.first == "points8") -> {
                results.add("8")
            }

            (it.first == "points9") -> {
                results.add("9")
            }

            (it.first == "points10") -> {
                results.add("10")
            }

            (it.first == "points11") -> {
                results.add("11")
            }

            (it.first == "points12") -> {
                results.add("12")
            }

            (it.first == "points13") -> {
                results.add("13")
            }

            (it.first == "points14") -> {
                results.add("14")
            }

            (it.first == "points15") -> {
                results.add("15")
            }

            (it.first == "points16") -> {
                results.add("16")
            }

            (it.first == "points17") -> {
                results.add("17")
            }

            (it.first == "points18") -> {
                results.add("18")
            }

            (it.first == "points19") -> {
                results.add("19")
            }

            (it.first == "points20") -> {
                results.add("20")
            }

            (it.first == "points21") -> {
                results.add("21")
            }

            (it.first == "points22") -> {
                results.add("22")
            }

            (it.first == "points23") -> {
                results.add("23")
            }

            (it.first == "points24") -> {
                results.add("24")
            }

            else -> {
                Log.d("TAG", "assay13 -> else")
            }

        }
    }

    return getResultCompletedAssay(keyList = results, keyAssay = key)
}