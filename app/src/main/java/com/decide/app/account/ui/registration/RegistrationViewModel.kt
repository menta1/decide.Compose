package com.decide.app.account.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.domain.useCase.CreateUserUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state

    fun updateData(event: RegistrationEvent) {

        when (event) {
            is RegistrationEvent.SetEmail -> {
                _state.update { state ->
                    state.copy(
                        email = event.email, isErrorPassword = ValidationPassword.NO_ERROR
                    )
                }
            }

            is RegistrationEvent.SetPassword -> {
                _state.update { state ->
                    state.copy(
                        password = event.password, isErrorPassword = ValidationPassword.NO_ERROR
                    )
                }
            }

            is RegistrationEvent.SetConfirmPassword -> {
                _state.update { state ->
                    state.copy(
                        confirmPassword = event.password,
                        isErrorPassword = ValidationPassword.NO_ERROR
                    )
                }
            }

            RegistrationEvent.TryRegistration -> {
                when {
                    (_state.value.password.length < 6) -> {
                        _state.update { state ->
                            state.copy(
                                isErrorPassword = ValidationPassword.INVALID_CHARS
                            )
                        }
                    }

                    (_state.value.confirmPassword.length < 6) -> {
                        _state.update { state ->
                            state.copy(
                                isErrorPasswordConfirm = ValidationPassword.INVALID_CHARS
                            )
                        }
                    }

                    (_state.value.password != _state.value.confirmPassword) -> {
                        _state.update { state ->
                            state.copy(
                                isErrorPassword = ValidationPassword.NOT_MATCH
                            )
                        }
                    }

                    else -> {
                        if (checkEmailError()) {
                            _state.update { state ->
                                state.copy(
                                    uiState = UIState.REGISTRATION
                                )
                            }
                            viewModelScope.launch {
                                createUserUseCase.invoke(user = UserAuth(
                                    name = "",
                                    password = _state.value.password,
                                    email = _state.value.email,
                                ), result = { response ->
                                    when (response) {
                                        is Resource.Error -> {
                                            when (response.error) {
                                                is DecideAuthException -> {
                                                    when (response.error) {
                                                        is DecideAuthException.DecideAuthUserCollision -> {
                                                            _state.update {
                                                                it.copy(
                                                                    isErrorEmail = ValidationEmail.USER_COLLISION,
                                                                    uiState = UIState.DATA_ENTRY
                                                                )
                                                            }
                                                        }

                                                        is DecideAuthException.NoInternet -> {
                                                            _state.update {
                                                                it.copy(
                                                                    uiState = UIState.NO_INTERNET
                                                                )
                                                            }
                                                        }

                                                        else -> {
                                                            _state.update {
                                                                it.copy(
                                                                    uiState = UIState.UNKNOWN_ERROR
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

                                                else -> {
                                                    _state.update {
                                                        it.copy(
                                                            uiState = UIState.UNKNOWN_ERROR
                                                        )
                                                    }
                                                }
                                            }

                                        }

                                        is Resource.Success -> {
                                            _state.update {
                                                it.copy(uiState = UIState.SUCCESS_REGISTRATION)
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }

                }
            }
        }
    }

    private fun emailHasErrors(): Boolean {
        return if (_state.value.email.isNotBlank()) {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()
        } else {
            false
        }
    }

    private fun checkEmailError(): Boolean {
        return when {
            emailHasErrors() -> {
                _state.update { state ->
                    state.copy(
                        isErrorEmail = ValidationEmail.INCORRECTLY
                    )
                }
                false
            }

            _state.value.email.isBlank() -> {
                _state.update { state ->
                    state.copy(
                        isErrorEmail = ValidationEmail.CANT_EMPTY
                    )
                }
                false
            }

            else -> {
                _state.update { state ->
                    state.copy(
                        isErrorEmail = ValidationEmail.NO_ERROR
                    )
                }
                true
            }
        }
    }

}