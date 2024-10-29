package com.decide.app.account.domain.impl

import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.SingInWithEmailUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class SingInWithEmailUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : SingInWithEmailUseCase {
    override suspend fun invoke(
        user: UserAuth,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    ) {
        repository.singInUserWithEmail(user, onResult)
    }
}