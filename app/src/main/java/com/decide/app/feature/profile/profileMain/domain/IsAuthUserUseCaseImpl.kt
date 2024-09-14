package com.decide.app.feature.profile.profileMain.domain

import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsAuthUserUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : IsAuthUserUseCase {
    override fun invoke(): Flow<Resource<ProfileUI, DecideException>> {
        return repository.isAuth()
    }
}