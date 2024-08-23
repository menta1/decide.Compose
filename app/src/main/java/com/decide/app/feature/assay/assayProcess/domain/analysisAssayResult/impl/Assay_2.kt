package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun assay2(answers: List<Answers>, key: KeyAssay): ResultCompletedAssay {

    var pointsAnxiety = 0f
    var pointsFrustration = 0f
    var pointsAggressiveness = 0f
    var pointsRigidity = 0f

    val shortResults = mutableListOf<String>()
    val results = mutableListOf<String>()
    val keyResults = mutableListOf<Int>()

    answers.forEach { answer ->
        when {
            (answer.idAnswer in 0..10) -> {
                pointsAnxiety += answer.answerValue
            }

            (answer.idAnswer in 11..20) -> {
                pointsFrustration += answer.answerValue
            }

            (answer.idAnswer in 21..30) -> {
                pointsAggressiveness += answer.answerValue
            }

            (answer.idAnswer in 31..40) -> {
                pointsRigidity += answer.answerValue
            }
        }
    }

    when {
        (pointsAnxiety in 0f..7f) -> {
            results.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(1)
        }

        (pointsAnxiety in 8f..14f) -> {
            results.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(2)
        }

        else -> {
            results.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(3)
        }
    }

    when {
        (pointsFrustration in 0f..7f) -> {
            results.add(key.result["4"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["4"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(4)
        }

        (pointsFrustration in 8f..14f) -> {
            results.add(key.result["5"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["5"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(5)
        }

        else -> {
            results.add(key.result["6"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["6"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(6)
        }
    }

    when {
        (pointsAggressiveness in 0f..7f) -> {
            results.add(key.result["7"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["7"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(7)
        }

        (pointsAggressiveness in 8f..14f) -> {
            results.add(key.result["8"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["8"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(8)
        }

        else -> {
            results.add(key.result["9"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["9"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(9)
        }
    }

    when {
        (pointsRigidity in 0f..7f) -> {
            results.add(key.result["10"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["10"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(10)
        }

        (pointsRigidity in 8f..14f) -> {
            results.add(key.result["11"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["11"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(11)
        }

        else -> {
            results.add(key.result["12"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["12"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(12)
        }
    }

    return ResultCompletedAssay(
        date = Date().time,
        shortResults = shortResults,
        results = results,
        keyResults = keyResults
    )
}