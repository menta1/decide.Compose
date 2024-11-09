package com.decide.app.account.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.domain.useCase.SingInWithEmailUseCase
import com.decide.app.account.domain.useCase.SingInWithVKUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.ui.validators.ValidationEmail
import com.decide.app.account.ui.validators.ValidationPassword
import com.decide.app.feature.profile.settings.ui.SettingsScreenViewModel.Companion.LINK_PRIVACY_POLICY
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val singInWithEmailUseCase: SingInWithEmailUseCase,
    private val singInWithVKUseCase: SingInWithVKUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.SetEmail -> {
                _state.update { state ->
                    state.copy(
                        email = event.email, isErrorEmail = ValidationEmail.NO_ERROR
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
                if (_state.value.email.isNotEmpty() && _state.value.password.isNotEmpty()) {
                    _state.update { state ->
                        state.copy(
                            uiState = UIState.PROCESS_AUTH
                        )
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        singInWithEmailUseCase.invoke(user = UserAuth(
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
                                            _state.update { state ->
                                                state.copy(
                                                    uiState = UIState.SUCCESS_AUTH
                                                )
                                            }
                                        }

                                    }
                                }

                                is Resource.Error -> {
                                    when (response.error) {
                                        is DecideAuthException.NoInternet -> {
                                            _state.update {
                                                it.copy(
                                                    uiState = UIState.NETWORK_ERROR
                                                )
                                            }
                                        }

                                        is DecideAuthException.DecideAuthInvalidUser -> {
                                            _state.update {
                                                it.copy(
                                                    email = "",
                                                    password = "",
                                                    exceptionAuth = true,
                                                    uiState = UIState.DATA_ENTRY
                                                )
                                            }
                                        }

                                        else -> {
                                            _state.update {
                                                it.copy(
                                                    uiState = UIState.ERROR
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                        })

                    }
                    checkEmailError()
                } else {
                    _state.update { state ->
                        state.copy(
                            uiState = UIState.DATA_ENTRY
                        )
                    }
                    if (_state.value.password.isEmpty()) {
                        _state.update { it.copy(isErrorPassword = ValidationPassword.INVALID_CHARS) }
                    }
                    if (_state.value.email.isEmpty()) {
                        _state.update { it.copy(isErrorEmail = ValidationEmail.CANT_EMPTY) }
                    }
                }
            }

            is LoginScreenEvent.EmailFocused -> {
                checkEmailError()
            }

            LoginScreenEvent.AuthWithVK -> {
                _state.update {
                    it.copy(
                        uiState = UIState.PROCESS_AUTH
                    )
                }
                viewModelScope.launch {
                    singInWithVKUseCase.invoke {
                        when (it) {
                            is Resource.Success -> {
                                Timber.tag("TAG").d("AuthWithVK SUCCESS_AUTH")
                                _state.update { state ->
                                    state.copy(
                                        uiState = UIState.SUCCESS_AUTH
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _state.update { state ->
                                    state.copy(
                                        uiState = UIState.DATA_ENTRY
                                    )
                                }
                            }
                        }
                    }
                }
            }

            LoginScreenEvent.PrivacyPolicy -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
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