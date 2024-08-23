package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl.AnalysisKeyAssayImpl.Companion.NO_KEY_FOR_ASSAY
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun getResultCompletedAssay(key: String, keyAssay: KeyAssay) = ResultCompletedAssay(
    date = Date().time,
    shortResults = listOf(keyAssay.resultShort[key] ?: NO_KEY_FOR_ASSAY),
    results = listOf(keyAssay.result[key] ?: NO_KEY_FOR_ASSAY),
    keyResults = listOf(key.toInt())
)