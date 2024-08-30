package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay21(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var extra = 0f
    var intro = 0f

    answers.forEach { answer ->
        if (answer.answerValue > 0) {
            extra += answer.answerValue
        } else {
            intro += answer.answerValue
        }
    }

    val result = (extra + intro) / 32

    return if (result > 0) {
        getResultCompletedAssay("1", key)
    } else {
        getResultCompletedAssay("2", key)
    }

}