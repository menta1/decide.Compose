package com.decide.app.account.domain.useCase

import com.decide.app.account.modal.UserUpdate
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface UpdateUserDataUseCase {
    suspend operator fun invoke(userUpdate: UserUpdate): Resource<Boolean, DecideException>
}