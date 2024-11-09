package com.decide.app.feature.assay.assayMain.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.adsManager.AccountInfo
import com.decide.app.account.adsManager.AdsManager
import com.decide.app.activity.domain.InitApp
import com.decide.app.feature.assay.assayMain.data.AssayMainRepository
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayUI
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AssayMainViewModel @Inject constructor(
    private val repository: AssayMainRepository,
    private val initApp: InitApp,
    @ApplicationContext private val context: Context,
    private val adsManager: AdsManager
) : ViewModel() {

    private val _state = MutableStateFlow(AssayMainState())
    val state: StateFlow<AssayMainState> = _state.asStateFlow()

    private var adsInfo: AccountInfo? = null
    private var lastScrollIndex = 0
    private var nowDownloadAttempts = 0
    private var width: Int = -1
    private val adsList: MutableList<BannerAdView> = mutableListOf()

    private var assayUIList = emptyList<AssayUI>()

    init {
        viewModelScope.launch {
            adsInfo = adsManager.getAccountInfo()
            getAssays()
        }
    }

    private fun getAd(width: Int) = BannerAdView(
        context = context,
    ).apply {
        Timber.tag("TAG").d("BannerAdView")
        setAdSize(
            BannerAdSize.inlineSize(
                context = context, width = width - 28, maxHeight = 100
            )
        )
        setAdUnitId("R-M-12145706-1")
        setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
                Timber.tag("TAG").d("onAdLoaded")
            }

            override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                Timber.tag("TAG").d("onAdFailedToLoad")
            }

            override fun onAdClicked() {
                Timber.tag("TAG").d("onAdClicked")
            }

            override fun onLeftApplication() {
                Timber.tag("TAG").d("onLeftApplication")
            }

            override fun onReturnedToApplication() {
                Timber.tag("TAG").d("onReturnedToApplication")
            }

            override fun onImpression(impressionData: ImpressionData?) {
                Timber.tag("TAG").d("onImpression")
            }
        })
        loadAd(
            AdRequest.Builder().setAge(adsInfo?.dateBirth).setGender(adsInfo?.gender).build()
        )
    }

    private suspend fun getAssays() {
        when (val result = repository.getAssays()) {
            is Resource.Success -> {
                result.data.collect { assays ->
                    if (assays.isEmpty()) {
                        _state.update { state ->
                            state.copy(
                                uiState = UIState.LOADING
                            )
                        }
                    } else {
                        _state.update { state ->
                            state.copy(
                                uiState = UIState.SUCCESS,
                                assays = assays.map { it.toAssayUI() })
                        }
                        assayUIList = assays.map { it.toAssayUI() }
                    }
                }
            }

            is Resource.Error -> {
                when (result.error) {
                    is DecideException.NoInternet -> {
                        _state.update { state ->
                            state.copy(
                                uiState = UIState.NO_INTERNET,
                            )
                        }
                    }

                    is DecideException.NoFindElementDB -> {
                        if (nowDownloadAttempts <= DOWNLOAD_ATTEMPTS) {
                            _state.update { state ->
                                state.copy(
                                    uiState = UIState.LOADING,
                                )
                            }
                            viewModelScope.launch {
                                delay(500)
                                getAssays()
                                nowDownloadAttempts++
                            }

                        } else {
                            _state.update { state ->
                                state.copy(
                                    uiState = UIState.ERROR,
                                )
                            }
                        }
                    }

                    else -> {
                        _state.update { state ->
                            state.copy(
                                uiState = UIState.ERROR,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AssayMainEvent) {
        when (event) {
            is AssayMainEvent.SetSearch -> {
                _state.update { state ->
                    state.copy(
                        searchText = event.search,
                        assays = if (event.search.isBlank()) {
                            assayUIList.toImmutableList()
                        } else {
                            assayUIList.filter { it.name.contains(event.search, ignoreCase = true) }
                                .toImmutableList()
                        })
                }
            }

            is AssayMainEvent.Update -> {
                viewModelScope.launch(Dispatchers.IO) {
                    initApp.initApp()
                    delay(1000)
                    getAssays()
                }
            }

            is AssayMainEvent.ScrollState -> {
                if (event.newScrollIndex == lastScrollIndex || _state.value.searchText.isNotEmpty()) return

                _state.update { state ->
                    state.copy(scrollUp = event.newScrollIndex > lastScrollIndex)
                }
                lastScrollIndex = event.newScrollIndex
            }

            is AssayMainEvent.LoadAds -> {
                viewModelScope.launch {
                    Timber.tag("TAG").d("AssayMainEvent.LoadAds launch")
                    adsManager.isShowBannerScreenAds().collect {
                        Timber.tag("TAG").d("AssayMainEvent.LoadAds collect")
                        if (it) {
                                width = event.width
                                Timber.tag("TAG").d("width $width")
                                Timber.tag("TAG").d("assayUIList ${assayUIList.size}")
                                if (adsList.isEmpty()) {
                                    repeat(assayUIList.size / 9) {
                                        Timber.tag("TAG").d("repeat")
                                        adsList.add(getAd(width))
                                    }
                                    _state.update { state ->
                                        state.copy(adView = adsList)
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val DOWNLOAD_ATTEMPTS = 3
    }
}