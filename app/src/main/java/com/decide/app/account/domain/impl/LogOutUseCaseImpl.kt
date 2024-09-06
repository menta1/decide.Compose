package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.LogOutUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : LogOutUseCase {
    override suspend fun invoke(onResult: (Resource<Boolean, DecideException>) -> Unit) {
        repository.logOut { onResult(it) }
    }
}