package com.decide.app.feature.assay.assayProcess.domain.impl

import com.decide.app.feature.assay.assayProcess.domain.AssayProcessRepository
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveResultUseCaseImpl @Inject constructor(
    private val repository: AssayProcessRepository, private val analysisKeys: AnalysisKeyAssay
) : SaveResultUseCase {
    override suspend fun invoke(
        id: Int,
        answers: List<Answers>,
        onResult: (Boolean) -> Unit
    ) {
        repository.getKeys(id = id, onResult = { result ->
            when (result) {
                is Resource.Success -> {
                    val endingResult = analysisKeys.invoke(
                        id = id, answers = answers, key = result.data
                    )
                    when (endingResult) {
                        is Resource.Error -> {
                            onResult(false)
                        }

                        is Resource.Success -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                repository.saveResult(id = id, result = endingResult.data)
                                onResult(true)
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    onResult(false)
                }
            }
        })
    }
}
