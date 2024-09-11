package com.decide.app.activity.domain

interface CheckForSync {
    suspend fun checkAssays()
}