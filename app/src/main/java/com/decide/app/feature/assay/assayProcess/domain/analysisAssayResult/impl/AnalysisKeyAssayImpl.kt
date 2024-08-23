package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import javax.inject.Inject

class AnalysisKeyAssayImpl @Inject constructor(
) : AnalysisKeyAssay {

    companion object {
        const val NO_KEY_FOR_ASSAY = "Нет результата"
    }

    override fun invoke(
        id: Int,
        answers: List<Answers>,
        key: KeyAssay
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

            4 -> {
                Resource.Success(assay4(answers = answers, key = key))
            }

            5 -> {
                Resource.Success(assay5(answers = answers, key = key))
            }

            6 -> {
                Resource.Success(assay6(answers = answers, key = key))
            }

            7 -> {
                Resource.Success(assay7(answers = answers, key = key))
            }

            8 -> {
                Resource.Success(assay8(answers = answers, key = key))
            }

            9 -> {
                Resource.Success(assay9(answers = answers, key = key))
            }

            else -> {
                Resource.Error(Exception("Нет такого ключа по id"))
            }
        }
    }
}

