package com.decide.app.feature.assay.assayMain.ui

sealed interface AssayMainEvent {
    class SetSearch(val search: String) : AssayMainEvent
    class Update() : AssayMainEvent
}