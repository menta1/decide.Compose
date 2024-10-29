package com.decide.app.account.adsManager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.decide.app.account.adsManager.Constants.BANNER_ADS_PREF
import com.decide.app.account.adsManager.Constants.FULL_SCREEN_ADS_PREF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdsManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AdsManager {

//    override suspend fun isShowAds(): Flow<Boolean> {
//        return dataStore.data.map {
//            it[ADS] == true
//        }
//    }

//    override suspend fun switchAds(): Boolean {
//        val result = isShowAds().first()
//        dataStore.edit {
//            it[ADS] = if (isShowAds().first()) NO_SHOW_ADS else SHOW_ADS
//        }
//        return !result
//    }

    override suspend fun isShowFullScreenAds(): Flow<Boolean> {
        val isShow = dataStore.data.map { it[FULL_SCREEN_ADS_PREF] }.filterNotNull().first()
        return if (isShow) {
            val nowCount = dataStore.data.map {
                it[ADS_COUNTER_FOR_FULL_SCREEN]
            }.first()
            if (nowCount == null) {
                dataStore.edit {
                    it[ADS_COUNTER_FOR_FULL_SCREEN] = 0
                }
                flowOf(false)
            } else {
                if (nowCount > 1) {
                    dataStore.edit {
                        it[ADS_COUNTER_FOR_FULL_SCREEN] = 0
                    }
                    flowOf(true)
                } else {
                    dataStore.edit {
                        it[ADS_COUNTER_FOR_FULL_SCREEN] = nowCount + 1
                    }
                    flowOf(false)
                }
            }
        } else {
            flowOf(false)
        }

    }

    override suspend fun isShowBannerScreenAds(): Flow<Boolean> {
        return dataStore.data.map { it[BANNER_ADS_PREF] }.filterNotNull()
    }

    companion object {

        private const val COUNTER_FULL_SCREEN_ADS = "count"
        val ADS_COUNTER_FOR_FULL_SCREEN = intPreferencesKey(
            name = COUNTER_FULL_SCREEN_ADS
        )

        private const val ADS_SELECTOR = "ADS"
        val ADS = booleanPreferencesKey(
            name = ADS_SELECTOR
        )
    }
}