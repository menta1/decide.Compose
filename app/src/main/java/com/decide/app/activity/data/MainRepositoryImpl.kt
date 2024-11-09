package com.decide.app.activity.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.decide.app.account.adsManager.Constants.BANNER_ADS_PREF
import com.decide.app.account.adsManager.Constants.FULL_SCREEN_ADS_PREF
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.account.statisticsClient.StatisticsClient
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.remote.RemoteAssayStorage
import com.decide.app.utils.Resource
import com.decide.uikit.theme.Themes
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val remoteConfig: FirebaseRemoteConfig,
    private val statisticsClient: StatisticsClient
) : MainRepository {

    override suspend fun initAssays() {
        val isDataInit = dataStore.data.map { it[DATA_INIT] }.firstOrNull()
        if (isDataInit == null || isDataInit == false) {
            Timber.tag("TAG").d("isDataInit != null && isDataInit == false")
            remoteStorage.getAssays { result ->
                when (result) {
                    is Resource.Success -> {
                        Timber.tag("TAG").d("getAssays Success")
                        coroutineScope.launch {
                            remoteStorage.getKeys()
                            remoteStorage.getCategories()
                            val userAuth = isUserAuthUseCase.invoke()
                            if (userAuth != null) {
                                remoteStorage.getPassedAssays(userAuth)
                                statisticsClient.getRemoteStatistic(userAuth, true)
                            }
                            dataStore.edit { it[DATA_INIT] = true }
                        }
                    }

                    is Resource.Error -> {
                        Timber.tag("TAG").d("getAssays Error")
                    }
                }
            }
        }
    }

    override suspend fun initCategories() {

    }

    override suspend fun initProfile() {

    }

    override suspend fun initTheme(): Flow<Themes> {
        return dataStore.data.map {
            when (it[SWITCH_THEME]) {
                1 -> Themes.LIGHT
                2 -> Themes.DARK
                else -> Themes.SYSTEM
            }
        }
    }

    override suspend fun switchTheme(themes: Themes) {
        dataStore.edit { it[SWITCH_THEME] = themes.value }
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
                Timber.tag("TAG").d("pre task.isSuccessful ")
                if (task.isSuccessful) {
                    Timber.tag("TAG").d("task.isSuccessful ")
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
        val DATA_INIT = booleanPreferencesKey(
            name = "date"
        )
        val SWITCH_THEME = intPreferencesKey(
            name = "theme"
        )
    }
}