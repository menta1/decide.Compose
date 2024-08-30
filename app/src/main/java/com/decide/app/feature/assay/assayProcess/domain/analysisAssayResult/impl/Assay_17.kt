package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun assay17(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var points = 0f

    answers.forEach { answer ->
        points += answer.answerValue
    }

    val shortResult = mutableListOf<String>()
    val result = mutableListOf<String>()
    val keyResults = mutableListOf<Int>()

    when {
        (points in 0f..8f) -> {
            shortResult.add(key.resultShort["0"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(0)
            keyResults.add(1)
        }

        (points == 9f) -> {
            shortResult.add(key.resultShort["1"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(1)
            keyResults.add(1)
        }

        (points == 10f) -> {
            shortResult.add(key.resultShort["2"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(2)
            keyResults.add(1)
        }

        (points == 11f) -> {
            shortResult.add(key.resultShort["3"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(3)
            keyResults.add(2)
        }

        (points == 12f) -> {
            shortResult.add(key.resultShort["4"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(4)
            keyResults.add(2)
        }

        (points == 13f) -> {
            shortResult.add(key.resultShort["5"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(5)
            keyResults.add(2)
        }

        (points == 14f) -> {
            shortResult.add(key.resultShort["6"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(6)
            keyResults.add(3)
        }

        (points == 15f) -> {
            shortResult.add(key.resultShort["7"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(7)
            keyResults.add(3)
        }

        (points == 16f) -> {
            shortResult.add(key.resultShort["8"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(8)
            keyResults.add(3)
        }

        else -> {
            shortResult.add(key.resultShort["9"] ?: NO_KEY_FOR_ASSAY)
            result.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(9)
            keyResults.add(3)
        }
    }

    return ResultCompletedAssay(
        date = Date().time,
        shortResults = shortResult,
        results = result,
        keyResults = keyResults
    )
}