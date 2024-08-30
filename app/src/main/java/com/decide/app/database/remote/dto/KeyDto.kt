package com.decide.app.database.remote.dto

import com.decide.app.database.local.dto.KeyEntity
import kotlinx.serialization.SerialName

data class KeyDto(
    @SerialName("id") val id: Int = -1,
    @SerialName("idCategory") val idCategory: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("nameCategory") val nameCategory: String = "",
    @SerialName("result") val result: Map<String, String> = emptyMap(),
    @SerialName("resultShort") val resultShort: Map<String, String> = emptyMap()
)

fun KeyDto.toKeyEntity() = KeyEntity(
    id = id,
    idCategory = idCategory,
    name = name,
    titleCategoryEng = nameCategory,
    result = result,
    resultShort = resultShort
)