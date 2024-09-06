package com.decide.app.account.domain.useCase

interface IsUserAuthUseCase {
    suspend operator fun invoke(): String?
}