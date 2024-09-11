package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun getResultCompletedAssay(
    key: String,
    keyAssay: KeyAssay,
    resultForStatistic: Double = -1.0
) = ResultCompletedAssay(
    date = Date().time,
    shortResults = listOf(keyAssay.resultShort[key] ?: NO_KEY_FOR_ASSAY),
    results = listOf(keyAssay.result[key] ?: NO_KEY_FOR_ASSAY),
    keyResults = listOf(key.toInt()),
    resultForStatistic = resultForStatistic
)

internal fun getResultCompletedAssay(
    keyList: List<String>,
    keyAssay: KeyAssay
): ResultCompletedAssay {

    val resultShort = mutableListOf<String>()
    keyList.forEach { resultShort.add(keyAssay.resultShort[it] ?: NO_KEY_FOR_ASSAY) }

    val result = mutableListOf<String>()
    keyList.forEach { result.add(keyAssay.result[it] ?: NO_KEY_FOR_ASSAY) }

    return ResultCompletedAssay(
        date = Date().time,
        shortResults = resultShort,
        results = result,
        keyResults = keyList.map { it.toInt() }
    )
}


