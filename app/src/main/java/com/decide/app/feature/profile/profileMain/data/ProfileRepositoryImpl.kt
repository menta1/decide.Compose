package com.decide.app.feature.profile.profileMain.data

import com.decide.app.account.SINGLE
import com.decide.app.account.domain.useCase.GetAvatarUseCase
import com.decide.app.account.domain.useCase.IsUserAuthUseCase
import com.decide.app.account.statisticsClient.TEMPERAMENT_CHOLERIC
import com.decide.app.account.statisticsClient.TEMPERAMENT_MELANCHOLIC
import com.decide.app.account.statisticsClient.TEMPERAMENT_PHLEGMATIC
import com.decide.app.account.statisticsClient.TEMPERAMENT_SANGUINE
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.toStatistic
import com.decide.app.feature.profile.profileMain.domain.ProfileRepository
import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.app.feature.profile.profileMain.modal.Statistic
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import com.decide.uikit.ui.statistic.modal.TemperamentUI
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
            val temperament = statistic.find { it.id == 2 }
            val depression = statistic.find { it.id == 3 }
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
                                        (anxiety.globalResults[SINGLE]?.div(anxiety.users))!!.toFloat()
                                    ) else null,
                                    depression = if (depression != null) Pair(
                                        depression.result.toFloat(),
                                        (depression.globalResults[SINGLE]?.div(depression.users))!!.toFloat()
                                    ) else null,
                                    temperament = if (temperament != null) parseTemperament(
                                        temperament.globalResults
                                    ) else null,
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
                                        (anxiety.globalResults[SINGLE]?.div(anxiety.users))!!.toFloat()
                                    ) else null,
                                    depression = if (depression != null) Pair(
                                        depression.result.toFloat(),
                                        (depression.globalResults[SINGLE]?.div(depression.users))!!.toFloat()
                                    ) else null,
                                    temperament = if (temperament != null) parseTemperament(
                                        temperament.globalResults
                                    ) else null,
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

    private fun parseTemperament(statistic: Map<String, Double>): TemperamentUI {
        var choleric = 0.0
        var sanguine = 0.0
        var phlegmatic = 0.0
        var melancholic = 0.0
        val sum = if (statistic.values.sum() < 4) {
            4.0
        } else {
            statistic.values.sum()
        }
        statistic.forEach {
            when (it.key) {
                TEMPERAMENT_CHOLERIC -> {
                    choleric = (it.value / sum) * 100
                }

                TEMPERAMENT_SANGUINE -> {
                    sanguine = (it.value / sum) * 100
                }

                TEMPERAMENT_PHLEGMATIC -> {
                    phlegmatic = (it.value / sum) * 100
                }

                TEMPERAMENT_MELANCHOLIC -> {
                    melancholic = (it.value / sum) * 100
                }
            }
        }
        return TemperamentUI(
            choleric = Pair(
                first = "Холерик",
                second = if (choleric != 0.0) choleric else (1.0 / sum) * 100
            ),
            sanguine = Pair(
                first = "Сангвиник",
                second = if (sanguine != 0.0) sanguine else (1.0 / sum) * 100
            ),
            phlegmatic = Pair(
                first = "Меланхолик",
                second = if (phlegmatic != 0.0) phlegmatic else (1.0 / sum) * 100
            ),
            melancholic = Pair(
                first = "Флегматик",
                second = if (melancholic != 0.0) melancholic else (1.0 / sum) * 100
            )
        )

    }
}