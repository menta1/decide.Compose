package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import com.decide.app.database.local.dto.AnswerAssayEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class AnswerConverter {
    @TypeConverter()
    fun fromAnswerList(answerList: List<AnswerAssayEntity>): String {
        return Json.encodeToString(answerList)
    }


    @TypeConverter()
    fun toAnswerList(jsonString: String): List<AnswerAssayEntity> {
        return Json.decodeFromString<List<AnswerAssayEntity>>(jsonString)
    }
}