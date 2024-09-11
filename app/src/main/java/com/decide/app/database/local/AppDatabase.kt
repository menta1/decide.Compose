package com.decide.app.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decide.app.database.local.convertors.AnswerConverter
import com.decide.app.database.local.convertors.KeysConverter
import com.decide.app.database.local.convertors.PassedAssayConverter
import com.decide.app.database.local.convertors.QuestionConvertor
import com.decide.app.database.local.convertors.ResultConvertor
import com.decide.app.database.local.dao.AssayDao
import com.decide.app.database.local.dao.CategoryDao
import com.decide.app.database.local.dao.KeyDao
import com.decide.app.database.local.dao.ProfileDao
import com.decide.app.database.local.dao.StatisticDao
import com.decide.app.database.local.entities.assay.AssayEntity
import com.decide.app.database.local.entities.CategoryEntity
import com.decide.app.database.local.entities.KeyEntity
import com.decide.app.database.local.entities.profile.ProfileEntity
import com.decide.app.database.local.entities.StatisticEntity

@Database(
    version = 1,
    entities = [
        AssayEntity::class,
        CategoryEntity::class,
        KeyEntity::class,
        ProfileEntity::class,
        StatisticEntity::class],
    exportSchema = false
)
@TypeConverters(
    QuestionConvertor::class,
    AnswerConverter::class,
    ResultConvertor::class,
    KeysConverter::class,
    PassedAssayConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assayDao(): AssayDao

    abstract fun categoryDao(): CategoryDao

    abstract fun keyDao(): KeyDao

    abstract fun profileDao(): ProfileDao

    abstract fun statisticsDao(): StatisticDao
}