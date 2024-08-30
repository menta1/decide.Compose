package com.decide.app.feature.assay.assayProcess.domain.useCase

import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers

interface SaveResultUseCase {
    suspend operator fun invoke(
        id: Int,
        answer: List<Answer>,
        answers: List<Answers>
    )
}