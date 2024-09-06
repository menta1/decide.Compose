package com.decide.app.account.domain.useCase

import android.net.Uri

interface SaveAvatarUseCase {
    operator fun invoke(uri: Uri)
}