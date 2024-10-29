package com.decide.app.feature.assay.assayProcess.domain.useCase

import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface GetAssayUseCase {
    suspend operator fun invoke(id: Int): Resource<Assay, DecideException>
}