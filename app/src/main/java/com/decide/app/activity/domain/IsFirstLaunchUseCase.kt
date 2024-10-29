package com.decide.app.activity.domain

interface IsFirstLaunchUseCase {
    suspend operator fun invoke(): Boolean
}