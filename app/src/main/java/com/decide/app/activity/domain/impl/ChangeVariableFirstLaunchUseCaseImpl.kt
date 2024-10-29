package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.ChangeVariableFirstLaunchUseCase
import javax.inject.Inject

class ChangeVariableFirstLaunchUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : ChangeVariableFirstLaunchUseCase {
    override suspend fun invoke() {
        repository.changeFirstLaunch()
    }
}