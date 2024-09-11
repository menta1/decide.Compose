package com.decide.app.utils

import com.decide.app.database.local.entities.assay.AnswerAssayEntity
import com.decide.app.database.local.entities.assay.AssayEntity
import com.decide.app.database.local.entities.assay.QuestionEntity
import com.decide.app.feature.assay.assayMain.modals.AnswerAssay
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.feature.assay.assayMain.modals.QuestionAssay
import com.decide.app.feature.assay.assayMain.ui.AssayUI


fun Assay.toAssayUI(): AssayUI {
    return AssayUI(
        id = id,
        name = name,
        idCategory = idCategory,
        nameCategory = nameCategory,
        countQuestions = questions.size.toString(),
        rating = rating,
    )
}

fun Assay.toAssayEntity(): AssayEntity {
    return AssayEntity(
        id = id,
        idCategory = idCategory,
        name = name,
        description = description,
        nameCategory = nameCategory,
        questions = convertToQuestionAssayEntity(questions),
        dateCreation = dateCreation,
        rating = rating,
        type = type,
        results = emptyList(),
        timeForQuestions = timeForQuestions,
        timeForTest = timeForTest
    )
}

fun QuestionAssay.toQuestionAssayEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        text = text,
        listAnswers = convertToAnswerAssayEntity(listAnswers),
        image = image,
        countResponses = countResponses
    )
}

fun AnswerAssay.toAnswerAssayEntity(): AnswerAssayEntity {
    return AnswerAssayEntity(
        id = id,
        text = text,
        value = value
    )
}

fun convertToQuestionAssayEntity(questionAssay: List<QuestionAssay>): List<QuestionEntity> {
    return questionAssay.map { it.toQuestionAssayEntity() }
}

fun convertToAnswerAssayEntity(answerAssay: List<AnswerAssay>): List<AnswerAssayEntity> {
    return answerAssay.map { it.toAnswerAssayEntity() }
}