package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import com.decide.app.database.local.dto.QuestionAssayEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class QuestionConvertor {
    @TypeConverter()
    fun fromQuestionList(questionList: List<QuestionAssayEntity>): String {
        return Json.encodeToString(questionList)
    }


    @TypeConverter()
    fun toQuestionList(jsonString: String): List<QuestionAssayEntity> {
        return Json.decodeFromString<List<QuestionAssayEntity>>(jsonString)
    }
}