package com.decide.app.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.decide.app.navigation.AppNavScreen
import com.decide.app.navigation.Assay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.theme.uiBackground
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShowAds {

    private val viewModel: MainActivityViewModel by viewModels()
    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initApp()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(uiBackground.toArgb(), Color.Black.toArgb())
        )

        setContent {
            DecideTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = DecideTheme.colors.background
                ) {
                    AppNavScreen(
                        startDestination = Assay.route,
                        ads = this
                    )
                }
            }
        }
    }

    override fun loadAds(
        onAdLoaded: (ad: InterstitialAd) -> Unit,
        onAdFailedToLoad: (adRequestError: AdRequestError) -> Unit
    ) {
        val adRequestConfiguration =
            AdRequestConfiguration.Builder("demo-interstitial-yandex").build()
        interstitialAdLoader = InterstitialAdLoader(this).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(ad: InterstitialAd) {
                    Timber.tag("TAG").d("onAdLoaded")
                    interstitialAd = ad
                    onAdLoaded(ad)
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                    Timber.tag("TAG").d("onAdFailedToLoad")
                    onAdFailedToLoad(adRequestError)
                }
            })
        }
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    override fun showAds(
        onAdShown: () -> Unit,
        onAdFailedToShow: (adError: AdError) -> Unit,
        onAdDismissed: () -> Unit,
        onAdClicked: () -> Unit,
        onAdImpression: (impressionData: ImpressionData?) -> Unit
    ) {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {
                    Timber.tag("TAG").d("onAdShown")
                    onAdShown()
                }

                override fun onAdFailedToShow(adError: AdError) {
                    Timber.tag("TAG").d("onAdFailedToShow")
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    onAdFailedToShow(adError)

                }

                override fun onAdDismissed() {
                    Timber.tag("TAG").d("onAdDismissed")
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    onAdDismissed()
                }

                override fun onAdClicked() {
                    Timber.tag("TAG").d("onAdClicked")
                    onAdClicked()
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
                    Timber.tag("TAG").d("onAdImpression")
                    onAdImpression(impressionData)
                }
            })
            show(this@MainActivity)
        }
    }

    override fun clearAds() {
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }

}