package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.InitApp
import javax.inject.Inject

class InitAppImpl @Inject constructor(
    private val mainRepository: MainRepository
) : InitApp {
    override suspend fun initApp() {
        mainRepository.initAssays()
        mainRepository.initCategories()
        mainRepository.initProfile()
    }
}