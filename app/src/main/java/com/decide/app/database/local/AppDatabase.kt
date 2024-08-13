package com.decide.app.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decide.app.database.local.convertors.AnswerConverter
import com.decide.app.database.local.convertors.QuestionConvertor
import com.decide.app.database.local.convertors.ResultConvertor
import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dao.CategoryDao
import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.database.local.dto.CategoryEntity

@Database(
    version = 1,
    entities = [AssayEntity::class, CategoryEntity::class],
    exportSchema = false
)
@TypeConverters(
    QuestionConvertor::class,
    AnswerConverter::class,
    ResultConvertor::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assayDao(): AssayDao

    abstract fun category(): CategoryDao
}