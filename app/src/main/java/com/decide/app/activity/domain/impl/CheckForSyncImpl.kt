package com.decide.app.activity.domain.impl

import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.CheckForSync
import javax.inject.Inject

class CheckForSyncImpl @Inject constructor(
    private val repository: MainRepository
) : CheckForSync {
    override suspend fun checkAssays() {
        repository.checkNewPassedAssay()
    }
}