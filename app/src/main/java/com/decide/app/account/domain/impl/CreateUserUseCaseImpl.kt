package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.CreateUserUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : CreateUserUseCase {
    override suspend fun invoke(
        user: UserAuth,
        result: (Resource<Boolean, DecideException>) -> Unit
    ) {
        repository.createUser(user, onResult = result)
    }
}