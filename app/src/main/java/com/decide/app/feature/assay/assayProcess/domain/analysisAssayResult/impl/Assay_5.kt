package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun assay5(
    answers: List<Answer>,
    key: KeyAssay
): ResultCompletedAssay {

    var anxiety = 0f
    var depression = 0f
    val multiplierStats = 0.8

    answers.forEach { answer ->
        when {
            (answer.idQuestion == 1) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion == 2) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion == 3) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion == 4) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion in 5..7) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion in 8..11) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion == 12) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion in 13..14) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion in 15..16) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion == 17) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion == 18) -> {
                depression += answer.answerValue
            }

            (answer.idQuestion == 19) -> {
                anxiety += answer.answerValue
            }

            (answer.idQuestion == 20) -> {
                depression += answer.answerValue
            }
        }
    }

    val shortResults = mutableListOf<String>()
    val results = mutableListOf<String>()
    val keyResults = mutableListOf<Int>()
    var resultForStatistics = 0.0

    when {
        (anxiety >= 1.28f) -> {
            results.add(key.result["1"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["1"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(1)
        }

        (-1.27f..1.27f).contains(anxiety) -> {
            results.add(key.result["2"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["2"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(2)
        }

        (anxiety <= -1.28f) -> {
            results.add(key.result["3"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["3"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(3)
        }
    }
    when {
        (depression >= 1.28f) -> {
            results.add(key.result["4"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["4"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(4)
            resultForStatistics = -20.0 * multiplierStats
        }

        (-1.27f..1.27f).contains(depression) -> {
            results.add(key.result["5"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["5"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(5)
            resultForStatistics = 10.0 * multiplierStats
        }

        (depression <= -1.28f) -> {
            results.add(key.result["6"] ?: NO_KEY_FOR_ASSAY)
            shortResults.add(key.resultShort["6"] ?: NO_KEY_FOR_ASSAY)
            keyResults.add(6)
            resultForStatistics = 20.0 * multiplierStats
        }
    }

    return ResultCompletedAssay(
        date = Date().time,
        shortResults = shortResults,
        results = results,
        keyResults = keyResults,
        resultForStatistic = resultForStatistics
    )
}