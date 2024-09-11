package com.decide.app.database.local.entities.profile

import androidx.room.PrimaryKey
import com.decide.app.database.local.entities.assay.ResultCompletedAssayEntity
import kotlinx.serialization.Serializable

@Serializable
data class PassedAssayEntity(
    @PrimaryKey val id: Int,
    val idCategory: Int,
    val name: String,
    val nameCategory: String,
    val rating: String,
    val results: List<ResultCompletedAssayEntity>
)
