package com.decide.app.database.local.entities.profile

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.database.local.entities.assay.AssayEntity

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey val id: String,
    val firstName: String = "",
    val lastName: String = "",
    val email: String,
    val dateBirth: Long = -1,
    val gender: String = "",
    val city: String = "",
    val dateRegistration: Long,
    val dateLastAddedResult: Long = -1,
//    val listAssayPassed: List<AssayEntity> = emptyList()
)
