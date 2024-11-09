package com.decide.app.account.ui.registration

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.authenticationClient.exception.DecideAuthException
import com.decide.app.account.domain.useCase.CreateUserUseCase
import com.decide.app.account.domain.useCase.SingInWithVKUseCase
import com.decide.app.account.modal.UserAuth
import com.decide.app.account.ui.validators.ValidationEmail
import com.decide.app.account.ui.validators.ValidationPassword
import com.decide.app.activity.domain.ChangeVariableFirstLaunchUseCase
import com.decide.app.feature.profile.settings.ui.SettingsScreenViewModel.Companion.LINK_PRIVACY_POLICY
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val changeVariableFirstLaunchUseCase: ChangeVariableFirstLaunchUseCase,
    private val singInWithVKUseCase: SingInWithVKUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) { changeVariableFirstLaunchUseCase.invoke() }
    }

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

            RegistrationEvent.AuthWithVK -> {
                _state.update {
                    it.copy(
                        uiState = UIState.REGISTRATION
                    )
                }
                viewModelScope.launch {
                    singInWithVKUseCase.invoke {
                        when (it) {
                            is Resource.Success -> {
                                _state.update { state ->
                                    state.copy(
                                        uiState = UIState.SUCCESS_REGISTRATION
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

            RegistrationEvent.PrivacyPolicy -> {
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