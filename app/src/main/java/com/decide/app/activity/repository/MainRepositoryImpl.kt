package com.decide.app.activity.repository

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.RemoteAssayStorage
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val localStorageStorage: AppDatabase,
    private val remoteStorage: RemoteAssayStorage
) : MainRepository {
    override suspend fun initAssays() {
        //checkVersion remote DB тогда обновлять базу//Сделать позже
        remoteStorage.getAssays()
    }

    override suspend fun initCategories() {
        remoteStorage.getCategories()
    }

    override suspend fun initProfile() {

    }
}