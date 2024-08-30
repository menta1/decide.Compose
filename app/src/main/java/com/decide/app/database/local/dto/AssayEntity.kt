package com.decide.app.database.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.passed.models.PassedAssay

@Entity(tableName = "assay_table")
data class AssayEntity(
    @PrimaryKey val id: Int,
    val idCategory: Int,
    val name: String,
    val description: String,
    val nameCategory: String,
    val questions: List<QuestionEntity>,
    val dateCreation: String,
    val type: Int,
    val timeForTest: Long,
    val timeForQuestions: Long,
    val rating: String,
    val results: List<ResultCompletedAssayEntity> = listOf()
)

fun AssayEntity.toAssay() = Assay(
    id = id,
    idCategory = idCategory,
    name = name,
    description = description,
    nameCategory = nameCategory,
    questions = convertToQuestionAssay(questions),
    dateCreation = dateCreation,
    type = type,
    timeForTest = timeForTest,
    timeForQuestions = timeForQuestions,
    rating = rating
)

fun AssayEntity.toPassedAssay() = PassedAssay(
    id = id,
    idCategory = idCategory,
    name = name,
    nameCategory = nameCategory,
    rating = rating,
    results = results.map { it.toResultCompletedAssay() }
)