package com.decide.app.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decide.app.feature.profile.profileMain.modal.Statistic

@Entity(tableName = "statistics")
data class StatisticEntity(
    @PrimaryKey val id: Int,//ID статистики
    val result: Double,//результат для статистики. Общее количество баллов всех тестов для статистики,
    val oldResult: Double = 0.0,//результат для статистики. Общее количество баллов всех тестов для статистики,
    val globalResults: Map<String, Double> = emptyMap(),
    val users: Int = 0,
    val date: Long
)

fun StatisticEntity.toStatistic() = Statistic(
    id = id,
    result = result,
    globalResults = globalResults,
    users = users,
    date = date
)