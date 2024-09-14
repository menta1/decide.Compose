package com.decide.app.account.ui.fillProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.domain.useCase.SaveAvatarUseCase
import com.decide.app.account.domain.useCase.UpdateUserDataUseCase
import com.decide.app.account.modal.UserUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillProfileViewModel @Inject constructor(
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FillProfileState())
    val state: StateFlow<FillProfileState> = _state

    fun onEvent(event: FillProfileEvent) {
        when (event) {
            FillProfileEvent.Continue -> {
                if (_state.value.firstName.isBlank()) {
                    _state.update { state ->
                        state.copy(
                            isErrorFirstName = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(uiState = UIState.LOADING)
                    }
                    viewModelScope.launch {
                        updateUserDataUseCase.invoke(
                            UserUpdate(
                                firstName = _state.value.firstName,
                                lastName = _state.value.secondName
                            )
                        )
                        _state.update {
                            it.copy(uiState = UIState.SUCCESS)
                        }
                    }
                }
            }

            is FillProfileEvent.SetFirstName -> {
                _state.update { state ->
                    state.copy(
                        firstName = event.firstName,
                        isErrorFirstName = false
                    )
                }
            }

            is FillProfileEvent.SetSecondName -> {
                _state.update { state ->
                    state.copy(
                        secondName = event.secondName
                    )
                }
            }

            is FillProfileEvent.SetUriAvatar -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveAvatarUseCase.invoke(event.uri)
                }
            }

            is FillProfileEvent.SetDateOFBirth -> {
                _state.update {
                    it.copy(
                        dateOFBirth = event.dateOfBirth
                    )
                }
            }
        }
    }

}