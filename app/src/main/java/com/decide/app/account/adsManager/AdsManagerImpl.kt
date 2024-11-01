package com.decide.app.account.adsManager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.decide.app.account.adsManager.Constants.BANNER_ADS_PREF
import com.decide.app.account.adsManager.Constants.FULL_SCREEN_ADS_PREF
import com.decide.app.database.local.dao.ProfileDao
import com.decide.app.database.local.entities.profile.toAccountInfo
import com.yandex.mobile.ads.common.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import javax.inject.Inject

class AdsManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileDao: ProfileDao
) : AdsManager {

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

    override suspend fun getAccountInfo(): AccountInfo? {
        val currentUser = profileDao.getAccountInfo()?.toAccountInfo() ?: return null
        val age = getAgeFromUtcEpochMillis(currentUser.dateBirth.toLong())
        val male = if (currentUser.gender == "Мужчина") Gender.MALE else Gender.FEMALE
        return currentUser.copy(
            dateBirth = age.toString(),
            gender = male
        )
    }

    private fun getAgeFromUtcEpochMillis(epochMillis: Long): Int {
        val birthDate = Instant.ofEpochMilli(epochMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val currentDate = LocalDate.now()
        val period = Period.between(birthDate, currentDate)
        return period.years
    }

    companion object {
        private const val COUNTER_FULL_SCREEN_ADS = "count"
        val ADS_COUNTER_FOR_FULL_SCREEN = intPreferencesKey(
            name = COUNTER_FULL_SCREEN_ADS
        )
    }
}