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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val isUserAuthUseCase: IsUserAuthUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val localStorage: AppDatabase
) : ProfileRepository {
    override fun isAuth(): Flow<Resource<ProfileUI, DecideException>> = flow {
        val profileId = isUserAuthUseCase.invoke()
        if (!profileId.isNullOrBlank()) {
            val profile = localStorage.profileDao().get(profileId)
            val statistic = localStorage.statisticsDao().getAll().map { it.toStatistic() }
            val anxiety = statistic.find { it.id == 1 }
            if (profile != null) {
                when (val uri = getAvatarUseCase.invoke()) {
                    is Resource.Error -> {
                        emit(
                            Resource.Success(
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
                        )
                    }

                    is Resource.Success -> {
                        emit(
                            Resource.Success(
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
                        )
                    }
                }
            } else {
                emit(
                    Resource.Error(
                        DecideException.UserNotAuthorization(
                            "profileLocalStorage = null", "ProfileRepositoryImpl-isAuth"
                        )
                    )
                )
            }

        } else {
            emit(
                Resource.Error(
                    DecideException.UserNotAuthorization(
                        "isUserAuthUseCase = null", "ProfileRepositoryImpl-isAuth"
                    )
                )
            )
        }
    }

    override suspend fun getStatistics(): List<Statistic> {
        return localStorage.statisticsDao().getAll().map { it.toStatistic() }
    }
}