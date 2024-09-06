package com.decide.app.feature.profile.settings.data

import com.decide.app.account.domain.useCase.LogOutUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val logOut: LogOutUseCase
) : SettingsRepository {
    override suspend fun logOut(onResult: (Resource<Boolean, DecideException>) -> Unit) {
        logOut.invoke { onResult(it) }
    }
}