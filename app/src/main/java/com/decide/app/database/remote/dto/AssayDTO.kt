package com.decide.app.database.remote.assay.dto

import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.feature.assay.mainAssay.modals.Assay
import kotlinx.serialization.SerialName

data class AssayDTO(
    @SerialName("id") val id: Int = -1,
    @SerialName("idCategory") val idCategory: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("nameCategory") val nameCategory: String = "",
    @SerialName("questions") val questions: List<QuestionAssayDTO> = emptyList(),
    @SerialName("dateCreation") val dateCreation: String = "",
    @SerialName("rating") val rating: String = "",
    @SerialName("type") val type: Int = -1
)

fun AssayDTO.toAssay() = Assay(
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
)