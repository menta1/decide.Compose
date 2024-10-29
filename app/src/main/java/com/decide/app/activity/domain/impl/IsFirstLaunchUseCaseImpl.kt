package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.IsFirstLaunchUseCase
import javax.inject.Inject

class IsFirstLaunchUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : IsFirstLaunchUseCase {
    override suspend fun invoke(): Boolean {
        return repository.checkFirstLaunch()
    }
}