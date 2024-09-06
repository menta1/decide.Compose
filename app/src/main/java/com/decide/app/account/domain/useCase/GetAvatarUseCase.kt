package com.decide.app.account.domain.useCase

import android.net.Uri
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface GetAvatarUseCase {
    suspend operator fun invoke(): Resource<Uri, DecideException>
}