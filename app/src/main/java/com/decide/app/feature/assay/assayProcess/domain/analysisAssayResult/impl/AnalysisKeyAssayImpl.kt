package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.feature.assay.assayProcess.KeyAssay
import com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.AnalysisKeyAssay
import com.decide.app.feature.assay.assayProcess.ui.Answer
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
        answer: List<Answer>,
        answers: List<Answers>,
        key: KeyAssay
    ): Resource<ResultCompletedAssay, Exception> {
        return when (id) {
            1 -> {
                Resource.Success(assay1(answers = answer, key = key))
            }

            2 -> {
                Resource.Success(assay2(answers = answer, key = key))
            }

            3 -> {
                Resource.Success(assay3(answers = answer, key = key))
            }

            4 -> {
                Resource.Success(assay4(answers = answer, key = key))
            }

            5 -> {
                Resource.Success(assay5(answers = answer, key = key))
            }

            6 -> {
                Resource.Success(assay6(answers = answer, key = key))
            }

            7 -> {
                Resource.Success(assay7(answers = answer, key = key))
            }

            8 -> {
                Resource.Success(assay8(answers = answer, key = key))
            }

            9 -> {
                Resource.Success(assay9(answers = answer, key = key))
            }

            10 -> {
                Resource.Success(assay10(answers = answer, key = key))
            }

            11 -> {
                Resource.Success(assay11(answers = answer, key = key))
            }

            12 -> {
                Resource.Success(assay12(answers = answer, key = key))
            }

            13 -> {
                Resource.Success(assay13(answers = answer, key = key))
            }

            14 -> {
                Resource.Success(assay14(answers = answers, key = key))
            }

            15 -> {
                Resource.Success(assay15(answers = answer, key = key))
            }

            16 -> {
                Resource.Success(assay16(answers = answer, key = key))
            }

            17 -> {
                Resource.Success(assay17(answers = answer, key = key))
            }

            18 -> {
                Resource.Success(assay18(answers = answer, key = key))
            }

            19 -> {
                Resource.Success(assay19(answers = answer, key = key))
            }

            20 -> {
                Resource.Success(assay20(answers = answer, key = key))
            }

            21 -> {
                Resource.Success(assay21(answers = answer, key = key))
            }

            22 -> {
                Resource.Success(assay22(answers = answer, key = key))
            }

            23 -> {
                Resource.Success(assay23(answers = answer, key = key))
            }

            24 -> {
                Resource.Success(assay24(answers = answer, key = key))
            }

            25 -> {
                Resource.Success(assay25(answers = answer, key = key))
            }

            26 -> {
                Resource.Success(assay26(answers = answer, key = key))
            }

            27 -> {
                Resource.Success(assay27(answers = answer, key = key))
            }

            28 -> {
                Resource.Success(assay28(answers = answer, key = key))
            }

            29 -> {
                Resource.Success(assay29(answers = answer, key = key))
            }

            30 -> {
                Resource.Success(assay30(answers = answer, key = key))
            }

            31 -> {
                Resource.Success(assay31(answers = answer, key = key))
            }

            32 -> {
                Resource.Success(assay32(answers = answer, key = key))
            }

            else -> {
                Resource.Error(Exception("Нет такого ключа по id"))
            }
        }
    }
}

