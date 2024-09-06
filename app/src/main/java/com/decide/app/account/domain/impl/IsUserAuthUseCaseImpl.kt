package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import javax.inject.Inject

class IsUserAuthUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : IsUserAuthUseCase {
    override suspend fun invoke(): String? {
        return repository.isUserAuth()
    }
}