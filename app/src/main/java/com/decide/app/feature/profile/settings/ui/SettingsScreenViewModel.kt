package com.decide.app.feature.profile.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.profile.settings.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState.Initial)
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            SettingsScreenEvent.LogOut -> {
                _state.update { SettingsScreenState.Loading }
                viewModelScope.launch(Dispatchers.IO) {
                    repository.logOut {
                        _state.update { SettingsScreenState.LogOut }
                    }
                }
            }
        }
    }
}