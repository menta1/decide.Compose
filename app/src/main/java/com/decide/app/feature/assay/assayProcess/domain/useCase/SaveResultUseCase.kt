package com.decide.app.feature.assay.assayProcess.domain.useCase

import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.utils.Resource

interface SaveResultUseCase {
    suspend operator fun invoke(
        id: Int,
        answers: List<Answers>,
        onResult: (Boolean) -> Unit
    )
}