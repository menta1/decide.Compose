package com.decide.app.account.domain.impl

import android.net.Uri
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.domain.useCase.SaveAvatarUseCase
import javax.inject.Inject

class SaveAvatarUseCaseImpl @Inject constructor(
    private val repository: AccountRepository
) : SaveAvatarUseCase {
    override fun invoke(uri: Uri) {
        repository.saveAvatarFromGallery(uri)
    }
}