package com.decide.app.feature.passed.ui.passedMain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.statisticsClient.StatisticsClient
import com.decide.app.feature.passed.data.PassedScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassedScreenViewModel @Inject constructor(
    private val repository: PassedScreenRepository,
    private val statisticsClient: StatisticsClient
) : ViewModel() {

    private val _state = MutableStateFlow(PassedScreenState.Initial)
    var state: StateFlow<PassedScreenState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val allAssaysWithResults = repository.fetchAllResults()
            if (allAssaysWithResults.isNotEmpty()) {
                _state.update {
                    PassedScreenState.Success(allAssaysWithResults)
                }
            } else {
                _state.update {
                    PassedScreenState.Empty
                }
            }
        }
    }

    fun onEvent() {

    }
}