package com.decide.app.feature.profile.editProfile.data

import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.dao.ProfileDao
import com.decide.app.database.local.entities.profile.toProfileEdit
import com.decide.app.feature.profile.editProfile.modal.ProfileEdit
import com.decide.app.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val localBD: ProfileDao
) : EditProfileRepository {

    override suspend fun getProfile(): ProfileEdit? {
        val id = isUserAuthUseCase.invoke()
        val uri = getAvatarUseCase.invoke()
        when (uri) {
            is Resource.Error -> Timber.tag("TAG").d("uri Error")
            is Resource.Success -> Timber.tag("TAG").d("uri Success")
        }
        return if (!id.isNullOrEmpty()) {
            val profile = localBD.get(id)?.toProfileEdit()
            if (uri is Resource.Success) {
                profile?.copy(avatar = uri.data)
            } else {
                profile
            }
        } else null
    }
}