package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GlobalResultsConvertor {

    @TypeConverter()
    fun fromGlobalResult(keyList: Map<String, Double>): String {
        return Json.encodeToString(keyList)
    }


    @TypeConverter()
    fun toGlobalResult(jsonString: String): Map<String, Double> {
        return Json.decodeFromString<Map<String, Double>>(jsonString)
    }
}