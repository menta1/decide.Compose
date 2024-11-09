package com.decide.app.feature.profile.settings.data

import com.decide.app.activity.data.MainRepository
import com.decide.app.feature.profile.settings.domain.SwitchThemesUseCase
import com.decide.uikit.theme.Themes
import javax.inject.Inject

class SwitchThemesUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : SwitchThemesUseCase {
    override suspend fun switchTheme(themes: Themes) {
        mainRepository.switchTheme(themes)
    }
}