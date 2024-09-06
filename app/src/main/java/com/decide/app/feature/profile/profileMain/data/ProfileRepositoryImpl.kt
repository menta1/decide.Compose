package com.decide.app.feature.profile.profileMain.data

import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.AppDatabase
import com.decide.app.feature.profile.profileMain.domain.ProfileRepository
import com.decide.app.feature.profile.profileMain.modal.ProfileHeader
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val localStorage: AppDatabase
) : ProfileRepository {
    override suspend fun isAuth(): Resource<ProfileHeader, DecideException> {
        val profileId = isUserAuthUseCase.invoke()
        if (!profileId.isNullOrBlank()) {
            val profile = localStorage.profileDao().get(profileId)
            if (profile != null) {
                when (val uri = getAvatarUseCase.invoke()) {
                    is Resource.Error -> {
                        return Resource.Success(
                            ProfileHeader(
                                firstName = profile.firstName,
                                lastName = profile.lastName,
                            )
                        )
                    }

                    is Resource.Success -> {
                        return Resource.Success(
                            ProfileHeader(
                                firstName = profile.firstName,
                                lastName = profile.lastName,
                                avatar = uri.data
                            )
                        )
                    }
                }
            } else {
                return Resource.Error(
                    DecideException.UserNotAuthorization(
                        "profileLocalStorage = null",
                        "ProfileRepositoryImpl-isAuth"
                    )
                )
            }

        } else {
            return Resource.Error(
                DecideException.UserNotAuthorization(
                    "isUserAuthUseCase = null",
                    "ProfileRepositoryImpl-isAuth"
                )
            )
        }
    }
}