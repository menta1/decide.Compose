package com.decide.app.feature.assay.assayMain.ui

import com.yandex.mobile.ads.banner.BannerAdView

data class AssayMainState(
    val searchText: String = "",
    val assays: List<AssayUI> = emptyList(),
    val uiState: UIState = UIState.LOADING,
    val scrollUp: Boolean = false,
    val adView: BannerAdView? = null
)

enum class UIState {
    LOADING,
    ERROR,
    NO_INTERNET,
    SUCCESS
}