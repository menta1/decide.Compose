package com.decide.app.activity.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.decide.app.account.adsManager.Constants.BANNER_ADS_PREF
import com.decide.app.account.adsManager.Constants.FULL_SCREEN_ADS_PREF
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.RemoteAssayStorage
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val localStorageStorage: AppDatabase,
    private val remoteStorage: RemoteAssayStorage,
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val dataStore: DataStore<Preferences>,
    private val remoteConfig: FirebaseRemoteConfig
) : MainRepository {

    override suspend fun initAssays() {
        //checkVersion remote DB тогда обновлять базу//Сделать позже
        remoteStorage.getAssays {
            coroutineScope.launch {
                remoteStorage.getKeys()
                val userAuth = isUserAuthUseCase.invoke()
                if (userAuth != null) {
                    remoteStorage.getPassedAssays(userAuth)
                }
            }
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

    override suspend fun checkAuth(): Boolean {
        return isUserAuthUseCase.invoke() != null
    }

    override suspend fun checkFirstLaunch(): Boolean {
        val result = dataStore.data.map { it[FIRST_LAUNCH] }.firstOrNull()
        return result == null
    }

    override suspend fun updateRemoteConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    coroutineScope.launch(Dispatchers.IO) {
                        dataStore.edit {
                            it[FULL_SCREEN_ADS_PREF] = remoteConfig.getBoolean("full_screen_ads")
                        }
                    }

                    coroutineScope.launch(Dispatchers.IO) {
                        dataStore.edit {
                            it[BANNER_ADS_PREF] = remoteConfig.getBoolean("banner_ads")
                        }
                    }

                    remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
                        override fun onUpdate(configUpdate: ConfigUpdate) {
                            if (configUpdate.updatedKeys.contains("full_screen_ads")) {
                                remoteConfig.activate().addOnCompleteListener { config ->
                                    if (config.isSuccessful) {
                                        val full = remoteConfig.getBoolean("full_screen_ads")
                                        coroutineScope.launch(Dispatchers.IO) {
                                            dataStore.edit {
                                                it[FULL_SCREEN_ADS_PREF] = full
                                            }
                                        }
                                    }
                                }
                            }

                            if (configUpdate.updatedKeys.contains("banner_ads")) {
                                remoteConfig.activate().addOnCompleteListener { config ->
                                    if (config.isSuccessful) {
                                        val banner = remoteConfig.getBoolean("banner_ads")
                                        coroutineScope.launch(Dispatchers.IO) {
                                            dataStore.edit {
                                                it[BANNER_ADS_PREF] = banner
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        override fun onError(error: FirebaseRemoteConfigException) {
                            Timber.tag("TAG")
                                .d(error, "Config update error with code: %s", error.code)
                        }
                    })
                } else {
                    Timber.tag("TAG").d("isSuccessful is not")
                }
            }
            .addOnFailureListener {
                Timber.tag("TAG")
                    .d(it, "Config update error with code: %s", it.message)
            }

    }

    override suspend fun changeFirstLaunch() {
        dataStore.edit {
            it[FIRST_LAUNCH] = false
        }
    }

    private companion object {

        val FIRST_LAUNCH = booleanPreferencesKey(
            name = "isFirstLaunch"
        )
    }
}