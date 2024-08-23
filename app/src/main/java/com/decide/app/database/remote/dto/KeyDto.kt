package com.decide.app.database.remote.assay.dto

import com.decide.app.database.local.dto.KeyEntity
import kotlinx.serialization.SerialName

data class KeyDto(
    @SerialName("id") val id: Int = -1,
    @SerialName("name") val name: String = "",
    @SerialName("titleCategoryEng") val titleCategoryEng: String = "",
    @SerialName("result") val result: Map<String, String> = emptyMap(),
    @SerialName("resultShort") val resultShort: Map<String, String> = emptyMap()
)

fun KeyDto.toKeyEntity() = KeyEntity(
    id = id,
    name = name,
    titleCategoryEng = titleCategoryEng,
    result = result,
    resultShort = resultShort
)