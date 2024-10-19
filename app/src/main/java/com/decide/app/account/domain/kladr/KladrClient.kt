package com.decide.app.account.domain.kladr

import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface KladrClient {
    suspend fun getCities(request: String): Resource<List<String>, DecideException>
}