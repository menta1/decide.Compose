package com.decide.app.utils

import com.decide.app.database.local.dto.AnswerAssayEntity
import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.database.local.dto.QuestionEntity
import com.decide.app.feature.assay.mainAssay.modals.AnswerAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.app.feature.assay.mainAssay.ui.AssayUI


fun Assay.toAssayUI(): AssayUI {
    return AssayUI(
        id = id,
        name = name,
        idCategory = idCategory,
        nameCategory = nameCategory,
        countQuestions = countQuestions.size.toString(),
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
        questions = convertToQuestionAssayEntity(countQuestions),
        dateCreation = dateCreation,
        rating = rating,
        type = type,
        results = emptyList()
    )
}

fun QuestionAssay.toQuestionAssayEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        text = text,
        listAnswers = convertToAnswerAssayEntity(listAnswers),
        image = image
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