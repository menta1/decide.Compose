package com.decide.app.database.remote.dto

import androidx.annotation.Keep
import com.decide.app.database.local.entities.assay.AssayEntity
import kotlinx.serialization.SerialName

@Keep
data class AssayDTO(
    @SerialName("id") val id: Int = -1,
    @SerialName("idCategory") val idCategory: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("nameCategory") val nameCategory: String = "",
    @SerialName("questions") val questions: List<QuestionAssayDTO> = emptyList(),
    @SerialName("dateCreation") val dateCreation: String = "",
    @SerialName("type") val type: Int = -1,
    @SerialName("timeForTest") val timeForTest: Long = -1,
    @SerialName("timeForQuestions") val timeForQuestions: Long = -1,
    @SerialName("rating") val rating: String = "",
    @SerialName("results") val results: List<ResultCompletedAssayDTO> = listOf()
)

fun AssayDTO.toAssayEntity() = AssayEntity(
    id = id,
    idCategory = idCategory,
    name = name,
    description = description,
    nameCategory = nameCategory,
    convertToQuestionEntity(questions),
    dateCreation = dateCreation,
    rating = rating,
    type = type,
    timeForQuestions = timeForQuestions,
    timeForTest = timeForTest,
    results = convertToResultCompletedAssayEntity(results)
)