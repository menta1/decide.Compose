package com.decide.app.account.ui.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.domain.AccountRepository
import com.decide.app.account.ui.validators.ValidationEmail
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
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            ForgotPasswordEvent.SendToEmail -> {
                if (!emailHasErrors()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(
                                uiState = UIState.LOADING
                            )
                        }
                        repository.passwordReset(email = _state.value.email) { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            uiState = UIState.UNKNOWN_ERROR
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            uiState = UIState.SUCCESS
                                        )
                                    }
                                }

                            }
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            isErrorEmail = ValidationEmail.INCORRECTLY
                        )
                    }
                }
            }

            is ForgotPasswordEvent.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
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
}