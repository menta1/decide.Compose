package com.decide.app.activity.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.activity.domain.InitApp
import com.decide.app.activity.domain.InitRemoteConfig
import com.decide.app.activity.domain.IsFirstLaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val initApp: InitApp,
    private val initRemoteConfig: InitRemoteConfig,
    private val isFirstLaunchUseCase: IsFirstLaunchUseCase
) : ViewModel() {

    private val _authState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val authState: StateFlow<MainState> = _authState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.update {
                it.copy(
                    isFirstLaunch = isFirstLaunchUseCase.invoke()
                )
            }
            initApp.initApp()
            initRemoteConfig.invoke()
        }
    }

    fun initApp() {

    }
}