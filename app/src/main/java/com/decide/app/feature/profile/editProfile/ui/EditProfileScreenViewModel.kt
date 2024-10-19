package com.decide.app.feature.profile.editProfile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.domain.kladr.KladrClient
import com.decide.app.account.domain.useCase.SaveAvatarUseCase
import com.decide.app.account.domain.useCase.UpdateUserDataUseCase
import com.decide.app.account.modal.UserUpdate
import com.decide.app.feature.profile.editProfile.data.EditProfileRepository
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditProfileScreenViewModel @Inject constructor(
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val repository: EditProfileRepository,
    private val kladrClient: KladrClient
) : ViewModel() {


    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = repository.getProfile()
            if (profile != null) {
                _state.update {
                    it.copy(
                        firstName = profile.firstName,
                        secondName = profile.lastName,
                        city = profile.city,
                        dateOFBirth = profile.dateBirth,
                        gender = profile.gender
                    )
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            EditProfileEvent.Continue -> {
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
                        val result = updateUserDataUseCase.invoke(
                            UserUpdate(
                                firstName = _state.value.firstName,
                                lastName = _state.value.secondName,
                                city = _state.value.city,
                                gender = _state.value.gender,
                                dateBirth = _state.value.dateOFBirth
                            )
                        )
                        when (result) {
                            is Resource.Error -> {
                                when (result.error) {
                                    is DecideException.NoInternet -> {
                                        _state.update {
                                            it.copy(uiState = UIState.NETWORK_ERROR)
                                        }
                                    }

                                    else -> {
                                        _state.update {
                                            it.copy(uiState = UIState.ERROR)
                                        }
                                    }
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        showSuccessDialog = true,
                                        uiState = UIState.DATA_ENTRY
                                    )
                                }
                                delay(2000)
                                _state.update {
                                    it.copy(uiState = UIState.SUCCESS)
                                }
                            }
                        }

                    }
                }
            }

            is EditProfileEvent.SetFirstName -> {
                _state.update { state ->
                    state.copy(
                        firstName = event.firstName, isErrorFirstName = false
                    )
                }
            }

            is EditProfileEvent.SetSecondName -> {
                _state.update { state ->
                    state.copy(
                        secondName = event.secondName
                    )
                }
            }

            is EditProfileEvent.SetUriAvatar -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveAvatarUseCase.invoke(event.uri)
                }
            }

            is EditProfileEvent.SetDateOFBirth -> {
                _state.update {
                    it.copy(
                        dateOFBirth = event.dateOfBirth
                    )
                }
            }

            is EditProfileEvent.SetGender -> {
                if (_state.value.gender != event.gender) {
                    _state.update {
                        it.copy(
                            gender = event.gender
                        )
                    }
                }
            }

            is EditProfileEvent.SearchCity -> {
                _state.update {
                    it.copy(
                        city = event.city,
                        uiState = UIState.SEARCH_CITY
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    when (val result = kladrClient.getCities(event.city)) {
                        is Resource.Error -> {

                            Timber.tag("TAG").d("ERROR")
                        }

                        is Resource.Success -> {
                            _state.update {
                                it.copy(cities = result.data)
                            }
                        }

                    }

                }
            }

            is EditProfileEvent.SetCity -> {
                _state.update {
                    it.copy(
                        city = event.city,
                        uiState = UIState.DATA_ENTRY
                    )
                }
            }


        }
    }
}