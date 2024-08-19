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
    val rating: String,
    val type: Int,
    val results: List<ResultCompletedAssayEntity>
)

fun AssayEntity.toAssay(): Assay {
    return Assay(
        id,
        idCategory,
        name,
        description,
        nameCategory,
        convertToQuestionAssay(questions),
        dateCreation,
        rating,
        type
    )
}

fun AssayEntity.toPassedAssay(): PassedAssay {
    return PassedAssay(
        id, idCategory, name, nameCategory, rating, results.map { it.toResultCompletedAssay() }
    )
}