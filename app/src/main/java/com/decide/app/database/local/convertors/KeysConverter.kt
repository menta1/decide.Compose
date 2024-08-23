package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class KeysConverter {

    @TypeConverter()
    fun fromKeyList(keyList: Map<String, String>): String {
        return Json.encodeToString(keyList)
    }


    @TypeConverter()
    fun toKeyList(jsonString: String): Map<String, String> {
        return Json.decodeFromString<Map<String, String>>(jsonString)
    }
}