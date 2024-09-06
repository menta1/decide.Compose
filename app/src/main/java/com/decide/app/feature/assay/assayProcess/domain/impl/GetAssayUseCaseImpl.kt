package com.decide.app.feature.assay.assayProcess.domain.impl

import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.utils.Resource
import javax.inject.Inject

class GetAssayUseCaseImpl @Inject constructor(
    private val repository: AssayProcessRepository
) : GetAssayUseCase {
    override suspend fun invoke(id: Int): Resource<Assay, Exception> {
        return repository.getAssay(id)
    }
}