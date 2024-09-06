package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileHeader
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface ProfileRepository {
    suspend fun isAuth(): Resource<ProfileHeader, DecideException>
}