package com.decide.app.activity

import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd

interface ShowAds {
    fun loadAds(
        onAdLoaded: (ad: InterstitialAd) -> Unit,
        onAdFailedToLoad: (adRequestError: AdRequestError) -> Unit,
    )

    fun showAds(
        onAdShown: () -> Unit,
        onAdFailedToShow: (adError: AdError) -> Unit,
        onAdDismissed: () -> Unit,
        onAdClicked: () -> Unit,
        onAdImpression: (impressionData: ImpressionData?) -> Unit,
    )

    fun clearAds()
}