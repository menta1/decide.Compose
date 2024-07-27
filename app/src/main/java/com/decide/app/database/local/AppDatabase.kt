package com.decide.app.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decide.app.database.local.convertors.AnswerConverter
import com.decide.app.database.local.convertors.QuestionConvertor
import com.decide.app.database.local.convertors.ResultConvertor
import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dto.AssayEntity

@Database(
    version = 1,
    entities = [AssayEntity::class],
    exportSchema = false
)
@TypeConverters(
    QuestionConvertor::class,
    AnswerConverter::class,
    ResultConvertor::class,
//    HashMapConverter::class,
//    ExamShortConvertors::class,
//    ProfileResultExamConvertors::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assayDao(): AssayDao
//    abstract fun userDao(): UserDao
//    abstract fun examAnswersDao(): ExamAnswersDao
//    abstract fun categoryDao(): CategoryDao
//    abstract fun resultsExamsDao(): ResultsExamsDao
}