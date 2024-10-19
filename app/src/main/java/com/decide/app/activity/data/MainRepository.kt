package com.decide.app.activity.data

interface MainRepository {
    suspend fun initAssays()
    suspend fun initCategories()
    suspend fun initProfile()
    suspend fun checkNewPassedAssay()
    suspend fun checkAuth(): Boolean
}