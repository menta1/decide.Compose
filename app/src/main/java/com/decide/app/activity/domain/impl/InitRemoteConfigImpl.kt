package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.InitRemoteConfig
import javax.inject.Inject

class InitRemoteConfigImpl @Inject constructor(
    private val mainRepository: MainRepository
) : InitRemoteConfig {
    override suspend fun invoke() {
        mainRepository.updateRemoteConfig()
    }
}