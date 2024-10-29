package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.SingInWithVKUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class SingInWithVKUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : SingInWithVKUseCase {
    override suspend fun invoke(onResult: (result: Resource<Boolean, DecideException>) -> Unit) {
        repository.singInUserWithVK(onResult)
    }
}