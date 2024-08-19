package com.decide.app.feature.assay.assayProcess.domain.impl

import com.decide.app.feature.assay.assayProcess.domain.AssayProcessRepository
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import javax.inject.Inject

class GetAssayUseCaseImpl @Inject constructor(
    private val repository: AssayProcessRepository
) : GetAssayUseCase {
    override suspend fun invoke(id: Int): Resource<Assay> {
        return repository.getAssay(id)
    }
}