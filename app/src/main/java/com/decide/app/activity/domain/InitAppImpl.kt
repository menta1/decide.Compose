package com.decide.app.activity.domain

import com.decide.app.activity.repository.MainRepository
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