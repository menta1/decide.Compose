package com.decide.app.account.ui.fillProfile

data class FillProfileState(
    val firstName: String = "",
    val secondName: String = "",
    val city: String = "",
    val cities: List<String> = emptyList(),
    val dateOFBirth: Long = -1,
    val gender: String = "",
    val isErrorFirstName: Boolean = false,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    LOADING,
    SUCCESS,
    ERROR,
    NETWORK_ERROR,
    DATA_ENTRY,
    SEARCH_CITY
}