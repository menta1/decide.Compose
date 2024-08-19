package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun getResultCompletedAssay(key: String, keyDto: KeyDto) = ResultCompletedAssay(
    date = Date().time,
    shortResult = keyDto.resultShort[key] ?: "-1",
    result = keyDto.result[key] ?: "-1",
    keyResult = listOf(key.toInt())
)