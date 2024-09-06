package com.decide.app.database.remote.dto

import com.decide.app.database.local.entities.CategoryEntity
import kotlinx.serialization.SerialName

data class CategoryDTO(
    @SerialName("id") val id: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("nameEng") val nameEng: String = "",
    @SerialName("colorBackground") val colorBackground: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("countAssays") val countAssays: Int = -1,

    )

fun CategoryDTO.toCategoryEntity() = CategoryEntity(
    id = id,
    name = name,
    nameEng = nameEng,
    colorBackground = colorBackground,
    description = description,
    countAssays = countAssays
)

