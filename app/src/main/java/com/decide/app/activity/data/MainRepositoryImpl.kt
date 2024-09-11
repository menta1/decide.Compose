package com.decide.app.activity.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.RemoteAssayStorage
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val localStorageStorage: AppDatabase,
    private val remoteStorage: RemoteAssayStorage,
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val dataStore: DataStore<Preferences>
) : MainRepository {
    override suspend fun initAssays() {
        //checkVersion remote DB тогда обновлять базу//Сделать позже
        remoteStorage.getAssays()
        val userAuth = isUserAuthUseCase.invoke()
        if (userAuth != null) {
            delay(1000)
            remoteStorage.getPassedAssays(userAuth)
        }
    }

    override suspend fun initCategories() {
        //checkVersion remote DB тогда обновлять базу//Сделать позже
        remoteStorage.getCategories()
    }

    override suspend fun initProfile() {//При Входе на следующий день нужно обновлять пройденные тесты

    }

    override suspend fun checkNewPassedAssay() {

    }
}