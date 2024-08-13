package com.decide.app.database.remote.assay.dto

import com.decide.app.feature.category.mainCategory.modals.Category
import kotlinx.serialization.SerialName

data class CategoryDTO(
    @SerialName("id") val id: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("nameEng") val nameEng: String = "",
    @SerialName("colorBackground") val colorBackground: String = "",
    @SerialName("description") val description: String = ""
)

fun CategoryDTO.toCategory() = Category(
    id = id,
    name = name,
    nameEng = nameEng,
    colorBackground = colorBackground,
    description = description
)
