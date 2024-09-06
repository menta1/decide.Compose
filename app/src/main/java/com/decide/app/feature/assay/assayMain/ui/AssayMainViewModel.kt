package com.decide.app.feature.assay.assayMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayMain.data.AssayMainRepository
import com.decide.app.feature.assay.assayMain.modals.Assay
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AssayMainViewModel @Inject constructor(
    private val repository: AssayMainRepository
) : ViewModel() {

    private val _state =
        repository.getAssays().map(::separationState)
            .stateIn(viewModelScope, SharingStarted.Lazily, AssayMainState.Loading)


    val state: StateFlow<AssayMainState> = _state

    fun onEvent() {
        state.value
    }

    private fun separationState(result: Resource<List<Assay>, DecideException>): AssayMainState {
        return when (result) {
            is Resource.Success -> {
                if (result.data.isEmpty()) {
                    AssayMainState.Loading
                } else {
                    AssayMainState.Loaded(result.data.map { it.toAssayUI() }.toImmutableList())
                }
            }

            is Resource.Error -> {
                when (result.error) {
                    is DecideException.NoFindElementDB -> {
                        Timber.tag("TAG").d(result.error.messageLog)
                        AssayMainState.Empty
                    }

                    else -> {
                        AssayMainState.Error(result.error.messageLog)
                    }
                }

            }

        }
    }

}