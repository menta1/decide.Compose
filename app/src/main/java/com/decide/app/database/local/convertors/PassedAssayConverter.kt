package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import com.decide.app.database.local.entities.profile.PassedAssayEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class PassedAssayConverter {
    @TypeConverter()
    fun fromResultList(resultList: List<PassedAssayEntity>): String {
        return Json.encodeToString(resultList)
    }


    @TypeConverter()
    fun toResultList(jsonString: String): List<PassedAssayEntity> {
        return Json.decodeFromString<List<PassedAssayEntity>>(jsonString)
    }
}