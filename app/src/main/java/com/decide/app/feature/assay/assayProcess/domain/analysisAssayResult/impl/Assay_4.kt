package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun assay4(answers: List<Answers>, key: KeyAssay): ResultCompletedAssay {
    var sincerity = 0f
    val listSincerity = listOf(6, 24, 36, 12, 18, 30, 42, 48, 54)

    var extraversion = 0f
    val listExtraversion = listOf(
        1,
        3,
        8,
        10,
        13,
        17,
        22,
        25,
        27,
        39,
        44,
        46,
        49,
        53,
        56,
        5,
        15,
        20,
        29,
        32,
        34,
        37,
        41,
        51
    )

    var neuroticism = 0f
    val listNeuroticism = listOf(
        2,
        4,
        7,
        9,
        11,
        14,
        16,
        19,
        21,
        23,
        26,
        28,
        31,
        33,
        35,
        38,
        40,
        43,
        45,
        47,
        50,
        52,
        55,
        57
    )

    val shortResults = mutableListOf<String>()
    val results = mutableListOf<String>()
    val keyResults = mutableListOf<Int>()

    answers.forEach { answer ->
        if (listSincerity.contains(answer.idAnswer)) {
            sincerity += answer.answerValue
        }
        if (listExtraversion.contains(answer.idAnswer)) {
            extraversion += answer.answerValue
        }
        if (listNeuroticism.contains(answer.idAnswer)) {
            neuroticism += answer.answerValue
        }
    }

    when {
        (sincerity in 0f..3f) -> {
            results.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(1)
        }

        (sincerity in 4f..6f) -> {
            results.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(2)
        }

        (sincerity in 7f..9f) -> {
            results.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(3)
        }
    }

    when {
        (extraversion in 0f..2f) -> {
            results.add(key.result["4"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["4"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(4)
        }

        (extraversion in 3f..6f) -> {
            results.add(key.result["5"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["5"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(5)
        }

        (extraversion in 7f..10f) -> {
            results.add(key.result["6"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["6"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(6)
        }

        (extraversion in 11f..14f) -> {
            results.add(key.result["7"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["7"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(7)
        }

        (extraversion in 15f..18f) -> {
            results.add(key.result["8"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["8"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(8)
        }

        (extraversion in 19f..22f) -> {
            results.add(key.result["9"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["9"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(9)
        }

        (extraversion in 23f..24f) -> {
            results.add(key.result["10"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["10"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(10)
        }
    }

    when {
        (extraversion in 0f..2f) -> {
            results.add(key.result["11"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["11"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(11)
        }

        (extraversion in 3f..6f) -> {
            results.add(key.result["12"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["12"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(12)
        }

        (extraversion in 7f..10f) -> {
            results.add(key.result["13"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["13"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(13)
        }

        (extraversion in 11f..14f) -> {
            results.add(key.result["14"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["14"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(14)
        }

        (extraversion in 15f..18f) -> {
            results.add(key.result["15"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["15"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(15)
        }

        (extraversion in 19f..22f) -> {
            results.add(key.result["16"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["16"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(16)
        }

        (extraversion in 23f..24f) -> {
            results.add(key.result["17"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["17"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(17)
        }
    }

    if (extraversion in 0f..12f) {
        if (neuroticism in 0f..12f) {
            results.add(key.result["20"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["20"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(20)
        } else {
            results.add(key.result["21"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["21"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(21)
        }
    } else {
        if (neuroticism in 0f..12f) {
            results.add(key.result["19"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["19"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(19)
        } else {
            results.add(key.result["18"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["18"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(18)
        }
    }

    return ResultCompletedAssay(
        date = Date().time,
        shortResults = shortResults,
        results = results,
        keyResults = keyResults
    )
}