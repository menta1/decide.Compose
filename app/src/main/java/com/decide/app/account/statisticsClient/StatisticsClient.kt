package com.decide.app.account.statisticsClient

interface StatisticsClient {
    suspend fun updateStatistic()
    suspend fun getRemoteStatistic(idUser: String)
}