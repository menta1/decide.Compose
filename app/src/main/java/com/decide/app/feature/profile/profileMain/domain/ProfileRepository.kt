package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.feature.profile.profileMain.modal.Statistic
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface ProfileRepository {
    suspend fun isAuth(): Resource<ProfileUI, DecideException>
    suspend fun getStatistics(): List<Statistic>
}