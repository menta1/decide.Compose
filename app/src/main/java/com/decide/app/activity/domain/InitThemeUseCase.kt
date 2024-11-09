package com.decide.app.activity.domain

import com.decide.uikit.theme.Themes
import kotlinx.coroutines.flow.Flow

interface InitThemeUseCase {
    suspend operator fun invoke(): Flow<Themes>
}