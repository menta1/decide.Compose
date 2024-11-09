package com.decide.app.activity.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.activity.ShowAds
import com.decide.app.navigation.AppNavScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AuthenticationRouteBranch
import com.decide.app.navigation.LaunchingScreensRoute
import com.decide.uikit.theme.DecideTheme
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShowAds {

    private val viewModel: MainActivityViewModel by viewModels()
    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initApp()
        enableEdgeToEdge()
        setContent {
            val state by viewModel.authState.collectAsStateWithLifecycle()
            val isFirstLaunch by remember { mutableStateOf(state.isFirstLaunch) }
            var isAuthenticate by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = Unit) {
                viewModel.authState.collect {
                    when (it.isAuth) {
                        MainLogicState.LOADING -> {}
                        MainLogicState.SUCCESS -> {
                            isAuthenticate = true
                        }

                        MainLogicState.ERROR -> {
                            isAuthenticate = false
                        }
                    }
                }
            }

            DecideTheme(
                theme = state.theme
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                        .systemBarsPadding(),
                    color = DecideTheme.colors.background
                ) {
                    AppNavScreen(
                        startDestination = if (isFirstLaunch) {
                            LaunchingScreensRoute.route
                        } else if (isAuthenticate) {
                            AuthenticationRouteBranch.route
                        } else {
                            Assay.route
                        },
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
                    interstitialAd = ad
                    onAdLoaded(ad)
                }

                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
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
                    onAdShown()
                }

                override fun onAdFailedToShow(adError: AdError) {
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    onAdFailedToShow(adError)

                }

                override fun onAdDismissed() {
                    interstitialAd?.setAdEventListener(null)
                    interstitialAd = null
                    onAdDismissed()
                }

                override fun onAdClicked() {
                    onAdClicked()
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
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