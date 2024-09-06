package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import com.decide.app.database.local.entities.ResultCompletedAssayEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class ResultConvertor {
    @TypeConverter()
    fun fromResultList(resultList: List<ResultCompletedAssayEntity>): String {
        return Json.encodeToString(resultList)
    }


    @TypeConverter()
    fun toResultList(jsonString: String): List<ResultCompletedAssayEntity> {
        return Json.decodeFromString<List<ResultCompletedAssayEntity>>(jsonString)
    }
}