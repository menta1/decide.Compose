package com.decide.app.account.ui.fillProfile

data class FillProfileState(
    val firstName: String = "",
    val secondName: String = "",
    val isErrorFirstName: Boolean = false,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    LOADING,
    SUCCESS,
    ERROR,
    DATA_ENTRY
}