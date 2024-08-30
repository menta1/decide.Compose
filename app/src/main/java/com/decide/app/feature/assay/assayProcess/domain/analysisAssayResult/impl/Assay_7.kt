package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.passed.models.ResultCompletedAssay

internal fun assay7(answers: List<Answer>, key: KeyAssay): ResultCompletedAssay {
    var iam = 0f
    var communication = 0f
    var business = 0f

    answers.forEach {
        if (it.answerValue == 1f) {
            iam++
        }
        if (it.answerValue == 2f) {
            communication++
        }
        if (it.answerValue == 3f) {
            business++
        }
    }

    return if (iam > communication && iam > business) {
        getResultCompletedAssay(key = "1", keyAssay = key)
    } else if (communication > iam && communication > business) {
        getResultCompletedAssay(key = "2", keyAssay = key)
    } else {
        getResultCompletedAssay(key = "3", keyAssay = key)
    }

}