package com.decide.app.feature.profile.profileMain.data

import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.toStatistic
import com.decide.app.feature.profile.profileMain.domain.ProfileRepository
import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.feature.profile.profileMain.modal.Statistic
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val localStorage: AppDatabase
) : ProfileRepository {
    override suspend fun isAuth(): Resource<ProfileUI, DecideException> {
        val profileId = isUserAuthUseCase.invoke()
        if (!profileId.isNullOrBlank()) {
            val profile = localStorage.profileDao().get(profileId)
            val statistic = localStorage.statisticsDao().getAll().map { it.toStatistic() }
            val anxiety = statistic.find { it.id == 1 }
            if (profile != null) {
//                Timber.tag("TAG").d("ProfileRepositoryImpl ${anxiety!!.result.toFloat()} -- ${(anxiety.globalResults / anxiety.users).toFloat()} ")
                when (val uri = getAvatarUseCase.invoke()) {
                    is Resource.Error -> {
                        return Resource.Success(
                            ProfileUI(
                                firstName = profile.firstName,
                                lastName = profile.lastName,
                                email = profile.email,
                                anxiety = if (anxiety != null) Pair(
                                    anxiety.result.toFloat(),
                                    (anxiety.globalResults / anxiety.users).toFloat()
                                ) else null
                            )
                        )
                    }

                    is Resource.Success -> {
                        return Resource.Success(
                            ProfileUI(
                                firstName = profile.firstName,
                                lastName = profile.lastName,
                                avatar = uri.data,
                                email = profile.email,
                                anxiety = if (anxiety != null) Pair(
                                    anxiety.result.toFloat(),
                                    (anxiety.globalResults / anxiety.users).toFloat()
                                ) else null
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

    override suspend fun getStatistics(): List<Statistic> {
        return localStorage.statisticsDao().getAll().map { it.toStatistic() }
    }
}