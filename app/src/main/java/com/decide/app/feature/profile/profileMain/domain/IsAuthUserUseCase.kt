package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface IsAuthUserUseCase {
    suspend operator fun invoke(): Resource<ProfileUI, DecideException>
}