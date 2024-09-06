package com.decide.app.feature.profile.profileMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.profile.profileMain.domain.IsAuthUserUseCase
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val isAuthUserUseCase: IsAuthUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Initial)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.UpdateProfile -> updateProfile()
        }
    }

    private fun updateProfile() {
        viewModelScope.launch {
            when (val profileHeader = isAuthUserUseCase.invoke()) {
                is Resource.Error -> {
                    when (profileHeader.error) {

                        is DecideException.UserNotAuthorization -> {
                            delay(1000)
                            if (_state.value != ProfileState.NotAuthorized) {
                                _state.update { ProfileState.NotAuthorized }
                                updateProfile()
                            }
                        }

                        else -> {}

                    }
                    Timber.tag("TAG").d(profileHeader.error.messageLog)
                }

                is Resource.Success -> {
                    _state.update {
                        ProfileState.Loaded(profileHeader.data)
                    }
                }
            }
        }
    }


}