package com.decide.app.account.domain.impl

import android.net.Uri
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class GetAvatarUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : GetAvatarUseCase {
    override suspend fun invoke(): Resource<Uri, DecideException> {
        return repository.getAvatar()
    }
}