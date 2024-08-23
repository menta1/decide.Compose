package com.decide.app.feature.assay.mainAssay.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.mainAssay.data.AssayMainRepository
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AssayMainViewModel @Inject constructor(
    private val repository: AssayMainRepository
) : ViewModel() {

    private val _state: StateFlow<StateAssay> =
        repository.getAssays().map(::separationState)
            .stateIn(viewModelScope, SharingStarted.Lazily, StateAssay.Loading)

    val state: StateFlow<StateAssay> = _state

    fun onEvent() {
        state.value
    }

    private fun separationState(result: Resource<List<Assay>>): StateAssay {
        return when (result) {
            is Resource.Error -> {
                StateAssay.Error(result.exception?.message ?: "")
            }

            is Resource.Success -> {
                StateAssay.Loaded(result.data.map { it.toAssayUI() }.toImmutableList())
            }
        }
    }

}