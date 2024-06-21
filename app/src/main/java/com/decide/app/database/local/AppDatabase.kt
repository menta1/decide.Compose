package com.decide.app.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decide.app.database.local.convertors.AnswerConverter
import com.decide.app.database.local.convertors.QuestionConvertor
import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dao.TempDao
import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.database.local.dto.TempEntity

@Database(
    version = 1,
    entities = [AssayEntity::class, TempEntity::class],
    exportSchema = false
)
@TypeConverters(
    QuestionConvertor::class,
    AnswerConverter::class,
//    HashMapConverter::class,
//    ExamShortConvertors::class,
//    ProfileResultExamConvertors::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assayDao(): AssayDao
    abstract fun tempDao(): TempDao
//    abstract fun userDao(): UserDao
//    abstract fun examAnswersDao(): ExamAnswersDao
//    abstract fun categoryDao(): CategoryDao
//    abstract fun resultsExamsDao(): ResultsExamsDao
}