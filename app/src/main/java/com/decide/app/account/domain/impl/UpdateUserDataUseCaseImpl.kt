package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.UpdateUserDataUseCase
import com.decide.app.account.modal.UserUpdate
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class UpdateUserDataUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : UpdateUserDataUseCase {
    override suspend fun invoke(userUpdate: UserUpdate): Resource<Boolean, DecideException> {
        return accountRepository.updateUser(userUpdate)
    }
}