package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.InitThemeUseCase
import com.decide.uikit.theme.Themes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InitThemeUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : InitThemeUseCase {
    override suspend fun invoke(): Flow<Themes> {
        return mainRepository.initTheme()
    }
}