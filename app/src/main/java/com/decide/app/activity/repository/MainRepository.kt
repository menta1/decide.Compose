package com.decide.app.activity.repository

interface MainRepository {
    suspend fun initAssays()
    suspend fun initCategories()
    suspend fun initProfile()
}