package com.decide.app.feature.assay.assayProcess.domain.impl

import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.AssayProcessRepository
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveResultUseCaseImpl @Inject constructor(
    private val repository: AssayProcessRepository,
    private val analysisKeys: AnalysisKeyAssay
) : SaveResultUseCase {

    override suspend fun invoke(
        id: Int,
        answer: List<Answer>,
        answers: List<Answers>
    ) {
        val endingResult = analysisKeys.invoke(
            id = id,
            answer = answer,
            answers = answers,
            key = repository.getKeys(id)
        )
        when (endingResult) {
            is Resource.Error -> {

            }

            is Resource.Success -> {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.saveResult(
                        id = id,
                        result = endingResult.data
                    )
                }
            }
        }
    }
}
