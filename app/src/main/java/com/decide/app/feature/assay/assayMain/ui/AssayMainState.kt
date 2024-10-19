package com.decide.app.feature.assay.assayMain.ui

data class AssayMainState(
    val searchText: String = "",
    val assays: List<AssayUI> = emptyList(),
    val uiState: UIState = UIState.LOADING,
    val scrollUp: Boolean = false
)

enum class UIState {
    LOADING,
    ERROR,
    NO_INTERNET,
    SUCCESS
}