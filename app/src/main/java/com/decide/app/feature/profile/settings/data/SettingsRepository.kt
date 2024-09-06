package com.decide.app.feature.profile.settings.data

import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface SettingsRepository {
    suspend fun logOut(onResult: (Resource<Boolean, DecideException>) -> Unit)
}