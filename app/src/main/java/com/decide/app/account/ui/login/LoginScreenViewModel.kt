package com.decide.app.account.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.domain.useCase.SingInUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.ui.registration.ValidationEmail
import com.decide.app.account.ui.registration.ValidationPassword
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val singInUseCase: SingInUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SetEmail -> {
                _state.update { state ->
                    state.copy(
                        email = event.email, isErrorPassword = ValidationPassword.NO_ERROR
                    )
                }
            }

            is LoginScreenEvent.SetPassword -> {
                _state.update { state ->
                    state.copy(
                        password = event.password, isErrorPassword = ValidationPassword.NO_ERROR
                    )
                }
            }

            LoginScreenEvent.TryAuth -> {
                _state.update { state ->
                    state.copy(
                        uiState = UIState.PROCESS_AUTH
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    singInUseCase.invoke(user = UserAuth(
                        name = "",
                        password = _state.value.password,
                        email = _state.value.email,
                    ), onResult = { response ->
                        when (response) {
                            is Resource.Success -> {
                                when (response.data) {
                                    true -> {
                                        _state.update { state ->
                                            state.copy(
                                                uiState = UIState.SUCCESS_AUTH
                                            )
                                        }
                                    }

                                    false -> {

                                    }

                                }
                            }

                            is Resource.Error -> {}
                        }

                    })

                }
                checkEmailError()
            }

            is LoginScreenEvent.EmailFocused -> {
                checkEmailError()
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

    private fun checkEmailError() {
        if (emailHasErrors()) {
            _state.update { state ->
                state.copy(
                    isErrorEmail = ValidationEmail.INCORRECTLY
                )
            }
        } else {
            _state.update { state ->
                state.copy(
                    isErrorEmail = ValidationEmail.NO_ERROR
                )
            }
        }
    }
}