package com.decide.app.feature.profile.editProfile.data

import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.dao.ProfileDao
import com.decide.app.database.local.entities.profile.toProfileEdit
import com.decide.app.feature.profile.editProfile.modal.ProfileEdit
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val localBD: ProfileDao
) : EditProfileRepository {

    override suspend fun getProfile(): ProfileEdit? {
        val id = isUserAuthUseCase.invoke()
        return if (!id.isNullOrEmpty()) {
            localBD.get(id)?.toProfileEdit()
        } else null
    }
}