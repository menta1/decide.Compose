package com.decide.app.database.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.feature.assay.mainAssay.modals.Assay

@Entity(tableName = "temp_table")
data class TempEntity(
    @PrimaryKey val id: Int,
    val idCategory: Int,
    val name: String,
    val description: String,
    val nameCategory: String,
    val countQuestions: List<QuestionAssayEntity>,
    val dateCreation: String,
    val rating: String,
    val type: Int
)


fun TempEntity.toAssay(): Assay {
    return Assay(
        id,
        idCategory,
        name,
        description,
        nameCategory,
        convertToQuestionAssay(countQuestions),
        dateCreation,
        rating,
        type
    )
}
