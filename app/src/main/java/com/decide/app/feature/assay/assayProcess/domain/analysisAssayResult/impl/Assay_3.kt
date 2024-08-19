package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay3(answers: List<Answers>, key: KeyDto): ResultCompletedAssay {

    var points = 0
    answers.forEach {
        points += it.answerValue
    }
    val result = points

    return when {
        (result <= 68 ) -> {
            getResultCompletedAssay(key = "2", keyDto = key)
        }

        else -> {
            getResultCompletedAssay(key = "1", keyDto = key)
        }
    }
}