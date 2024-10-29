package com.decide.app.account.adsManager

import androidx.datastore.preferences.core.booleanPreferencesKey

object Constants {
    const val DATABASE_VERSION = "database_version"
    const val FULL_SCREEN_ADS = "full_screen_ads"
    const val BANNER_ADS = "banner_ads"

    const val DEV_USE_CONFIG = "dev_use_config"

    val FULL_SCREEN_ADS_PREF = booleanPreferencesKey(
        name = FULL_SCREEN_ADS
    )
    val BANNER_ADS_PREF = booleanPreferencesKey(
        name = BANNER_ADS
    )

}