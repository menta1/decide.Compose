package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.SingInUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class SingInUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : SingInUseCase {
    override suspend fun invoke(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        repository.singInUser(user, onResult)
    }
}