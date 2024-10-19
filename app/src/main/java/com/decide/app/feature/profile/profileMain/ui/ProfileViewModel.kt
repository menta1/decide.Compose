package com.decide.app.feature.profile.profileMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.profile.profileMain.domain.IsAuthUserUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val isAuthUserUseCase: IsAuthUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Initial)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            isAuthUserUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            ProfileState.Loaded(result.data)
                        }
                    }

                    is Resource.Error -> {
                        when (result.error) {
                            is DecideException.UserNotAuthorization -> {
                                _state.update {
                                    ProfileState.NotAuthorized
                                }
                            }

                            else -> {
                                _state.update { ProfileState.Error("") }
                            }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: ProfileScreenEvent) {

    }


}