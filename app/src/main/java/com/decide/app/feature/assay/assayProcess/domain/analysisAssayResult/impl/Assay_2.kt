package com.decide.app.feature.assay.assayProcess.domain.analysisAssayResult.impl

import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.passed.models.ResultCompletedAssay
import java.util.Date

internal fun assay2(answers: List<Answers>, key: KeyDto): ResultCompletedAssay {

    var pointsAnxiety = 0
    var pointsFrustration = 0
    var pointsAggressiveness = 0
    var pointsRigidity = 0

    answers.forEach { answer ->
        when {
            (answer.idAnswer in 0..10) -> {
                pointsAnxiety += answer.answerValue
            }

            (answer.idAnswer in 11..20) -> {
                pointsFrustration += answer.answerValue
            }

            (answer.idAnswer in 21..30) -> {
                pointsAggressiveness += answer.answerValue
            }

            (answer.idAnswer in 31..40) -> {
                pointsRigidity += answer.answerValue
            }
        }
    }

    var shortResultAnxiety = "Тревожность: "
    var shortResultFrustration = "Фрустрация: "
    var shortResultAggressiveness = "Агрессивность: "
    var shortResultRigidity = "Ригидность: "

    val keys: MutableList<Int> = mutableListOf()

    var resultAnxiety = "Тревожность: "
    var resultFrustration = "Фрустрация: "
    var resultAggressiveness = "Агрессивность: "
    var resultRigidity = "Ригидность: "

    when {
        (pointsAnxiety in 0..7) -> {
            shortResultAnxiety += key.resultShort.getValue("1")
            resultAnxiety += key.result.getValue("1")
            keys.add(1)
        }

        (pointsAnxiety in 8..14) -> {
            shortResultAnxiety += key.resultShort.getValue("2")
            resultAnxiety += key.result.getValue("2")
            keys.add(2)
        }

        else -> {
            shortResultAnxiety += key.resultShort.getValue("3")
            resultAnxiety += key.result.getValue("3")
            keys.add(3)
        }
    }

    when {
        (pointsFrustration in 0..7) -> {
            shortResultFrustration += key.resultShort.getValue("4")
            resultFrustration += key.result.getValue("4")
            keys.add(4)
        }

        (pointsFrustration in 8..14) -> {
            shortResultFrustration += key.resultShort.getValue("5")
            resultFrustration += key.result.getValue("5")
            keys.add(5)
        }

        else -> {
            shortResultFrustration += key.resultShort.getValue("6")
            resultFrustration += key.result.getValue("6")
            keys.add(6)
        }
    }

    when {
        (pointsAggressiveness in 0..7) -> {
            shortResultAggressiveness += key.resultShort.getValue("7")
            resultAggressiveness  += key.result.getValue("7")
            keys.add(7)
        }

        (pointsAggressiveness in 8..14) -> {
            shortResultAggressiveness += key.resultShort.getValue("8")
            resultAggressiveness  += key.result.getValue("8")
            keys.add(8)
        }

        else -> {
            shortResultAggressiveness += key.resultShort.getValue("9")
            resultAggressiveness  += key.result.getValue("9")
            keys.add(9)
        }
    }

    when {
        (pointsRigidity in 0..7) -> {
            shortResultRigidity +=  key.resultShort.getValue("10")
            resultRigidity+=  key.result.getValue("10")
            keys.add(10)
        }

        (pointsRigidity in 8..14) -> {
            shortResultRigidity +=  key.resultShort.getValue("11")
            resultRigidity+=  key.result.getValue("11")
            keys.add(11)
        }

        else -> {
            shortResultRigidity +=  key.resultShort.getValue("12")
            resultRigidity+=  key.result.getValue("12")
            keys.add(12)
        }
    }

    return ResultCompletedAssay(
        date = Date().time,
        shortResult = "$shortResultAnxiety, $shortResultFrustration, $shortResultAggressiveness, $shortResultRigidity",
        result = "$resultAnxiety \n\n$resultFrustration \n\n$resultAggressiveness \n\n$resultRigidity",
        keyResult = keys
    )
}