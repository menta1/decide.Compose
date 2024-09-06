package com.decide.app.database.remote.dto

import com.decide.app.database.local.entities.PassedAssayEntity
import com.decide.app.database.local.entities.ResultCompletedAssayEntity

data class PassedAssayDTO(
    val id: Int = -1,
    val idCategory: Int = -1,
    val name: String = "",
    val nameCategory: String = "",
    val rating: String = "",
    val results: List<ResultCompletedAssayDTO> = emptyList()
)

fun PassedAssayDTO.toPassedAssayEntity() = PassedAssayEntity(
    id = id,
    idCategory = idCategory,
    name = name,
    nameCategory = nameCategory,
    rating = rating,
    results = convertToResultCompletedAssayEntity(results)
)

fun convertToResultCompletedAssayEntity(resultCompletedAssay: List<ResultCompletedAssayDTO>): List<ResultCompletedAssayEntity> {
    return resultCompletedAssay.map { it.toResultCompletedAssayEntity() }
}