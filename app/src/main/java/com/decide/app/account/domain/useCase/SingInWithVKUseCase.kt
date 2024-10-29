package com.decide.app.account.domain.useCase

import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface SingInWithVKUseCase {
    suspend operator fun invoke(
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    )
}