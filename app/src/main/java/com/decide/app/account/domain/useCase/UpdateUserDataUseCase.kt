package com.decide.app.account.domain.useCase

import com.decide.app.account.modal.UserUpdate

interface UpdateUserDataUseCase {
    suspend operator fun invoke(userUpdate: UserUpdate)
}