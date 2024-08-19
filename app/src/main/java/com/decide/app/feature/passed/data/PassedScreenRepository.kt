package com.decide.app.feature.passed.data

import com.decide.app.feature.passed.models.PassedAssay

interface PassedScreenRepository {
    suspend fun fetchAllResults(): List<PassedAssay>
}