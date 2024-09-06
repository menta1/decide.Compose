package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileHeader
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class IsAuthUserUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : IsAuthUserUseCase {
    override suspend fun invoke(): Resource<ProfileHeader, DecideException> {
        return repository.isAuth()
    }
}