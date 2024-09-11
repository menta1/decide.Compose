package com.decide.app.feature.assay.assayMain.ui

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AssayMainState(
    val searchText: String = "",
    val assays: ImmutableList<AssayUI> = persistentListOf(),
    val uiState: UIState = UIState.LOADING
)

enum class UIState {
    LOADING,
    ERROR,
    NO_INTERNET,
    UNKNOWN_ERROR,
    SUCCESS
}