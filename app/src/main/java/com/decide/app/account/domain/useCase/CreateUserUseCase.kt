package com.decide.app.account.domain.useCase

import com.decide.app.account.modal.UserAuth
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface CreateUserUseCase {
    suspend operator fun invoke(
        user: UserAuth,
        result: (Resource<Boolean, DecideException>) -> Unit
    )
}