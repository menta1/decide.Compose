package com.decide.app.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.activity.data.MainRepository
import com.decide.app.activity.domain.InitApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val initApp: InitApp,
    private val checkAuth: MainRepository
) : ViewModel() {
    fun initApp() {
        viewModelScope.launch(Dispatchers.IO) {
            initApp.initApp()
        }
    }
}