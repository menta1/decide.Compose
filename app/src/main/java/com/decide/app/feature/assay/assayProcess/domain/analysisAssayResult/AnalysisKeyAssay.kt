package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource

interface AnalysisKeyAssay {
    operator fun invoke(
        id: Int,
        answer: List<Answer>,
        answers: List<Answers>,
        key: KeyAssay
    ): Resource<ResultCompletedAssay>
}