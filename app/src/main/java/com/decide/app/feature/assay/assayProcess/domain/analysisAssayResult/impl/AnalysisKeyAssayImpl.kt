package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AnalysisKeyAssayImpl @Inject constructor(
) : AnalysisKeyAssay {

    override fun invoke(
        id: Int,
        answers: List<Answers>,
        key: KeyDto
    ): Resource<ResultCompletedAssay> {
        return when (id) {
            1 -> {
                Resource.Success(assay1(answers = answers, key = key))
            }

            2 -> {
                Resource.Success(assay2(answers = answers, key = key))
            }

            3 -> {
                Resource.Success(assay3(answers = answers, key = key))
            }

            else -> {
                Resource.Error(Exception("Нет такого ключа по id"))
            }
        }
    }
}

