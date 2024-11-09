package com.decide.app.activity.data

import com.decide.uikit.theme.Themes
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun initAssays()
    suspend fun initCategories()
    suspend fun initProfile()
    suspend fun initTheme(): Flow<Themes>
    suspend fun switchTheme(themes: Themes)
    suspend fun checkNewPassedAssay()
    suspend fun checkAuth(): Boolean
    suspend fun checkFirstLaunch(): Boolean
    suspend fun updateRemoteConfig()
    suspend fun changeFirstLaunch()
}