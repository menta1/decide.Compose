package com.decide.app.account.domain.useCase

import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface LogOutUseCase {
    suspend operator fun invoke(onResult: (Resource<Boolean, DecideException>) -> Unit)
}