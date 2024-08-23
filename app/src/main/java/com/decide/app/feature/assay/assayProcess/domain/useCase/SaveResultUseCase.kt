package com.decide.app.feature.assay.assayProcess.domain.useCase

import com.decide.app.feature.assay.assayProcess.ui.Answers

interface SaveResultUseCase {
    suspend operator fun invoke(
        id: Int,
        answers: List<Answers>,
        onResult: (Boolean) -> Unit
    )
}