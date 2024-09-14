package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.feature.profile.profileMain.modal.Statistic
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun isAuth(): Flow<Resource<ProfileUI, DecideException>>
    suspend fun getStatistics(): List<Statistic>
}