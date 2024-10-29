package com.decide.app.feature.assay.assayMain.ui

sealed interface AssayMainEvent {
    class SetSearch(val search: String) : AssayMainEvent
    data object Update : AssayMainEvent
    data class LoadAds(val width: Int) : AssayMainEvent
    data class ScrollState(val newScrollIndex: Int) : AssayMainEvent
}