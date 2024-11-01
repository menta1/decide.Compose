package com.decide.app.feature.profile.editProfile.ui

import android.net.Uri

data class EditProfileState(
    val firstName: String = "",
    val secondName: String = "",
    val city: String = "",
    val cities: List<String> = emptyList(),
    val dateOFBirth: Long = -1,
    val gender: String = "",
    val avatar: Uri? = null,
    val isErrorFirstName: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val uiState: UIState = UIState.DATA_ENTRY
)

enum class UIState {
    LOADING,
    ERROR,
    SUCCESS,
    NETWORK_ERROR,
    DATA_ENTRY,
    SEARCH_CITY
}