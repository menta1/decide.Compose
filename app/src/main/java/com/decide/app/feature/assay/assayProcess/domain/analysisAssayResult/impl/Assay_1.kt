package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay1(answers: List<Answers>, key: KeyDto): ResultCompletedAssay {
    var points = 0
    answers.forEach {
        points += it.answerValue
    }
    val result: Float = points.toFloat() / 20

    return when {
        (result in 0f..1f) -> {
            getResultCompletedAssay(key = "1", keyDto = key)
        }

        (result in 1f..2f) -> {
            getResultCompletedAssay(key = "2", keyDto = key)
        }

        (result in 2f..3f) -> {
            getResultCompletedAssay(key = "3", keyDto = key)
        }

        (result in 3f..3.4f) -> {
            getResultCompletedAssay(key = "4", keyDto = key)
        }

        else -> {
            getResultCompletedAssay(key = "5", keyDto = key)
        }
    }
}