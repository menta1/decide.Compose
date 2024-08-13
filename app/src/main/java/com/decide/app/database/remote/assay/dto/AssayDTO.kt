package com.decide.app.database.remote.assay.dto

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
//fun Assay.toAssayUI() = AssayUI(
//    id = id,
//    name = name,
//    nameCategory = nameCategory,
//    countQuestions = countQuestions.size.toString(),
//    rating = rating
//)