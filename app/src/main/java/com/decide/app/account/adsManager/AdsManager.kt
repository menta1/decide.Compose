package com.decide.app.account.adsManager

import kotlinx.coroutines.flow.Flow

interface AdsManager {
    suspend fun isShowFullScreenAds(): Flow<Boolean>
    suspend fun isShowBannerScreenAds(): Flow<Boolean>
}