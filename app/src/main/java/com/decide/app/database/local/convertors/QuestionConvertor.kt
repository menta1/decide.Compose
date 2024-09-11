package com.decide.app.database.local.convertors

import androidx.room.TypeConverter
import com.decide.app.database.local.entities.assay.QuestionEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class QuestionConvertor {
    @TypeConverter()
    fun fromQuestionList(questionList: List<QuestionEntity>): String {
        return Json.encodeToString(questionList)
    }


    @TypeConverter()
    fun toQuestionList(jsonString: String): List<QuestionEntity> {
        return Json.decodeFromString<List<QuestionEntity>>(jsonString)
    }
}