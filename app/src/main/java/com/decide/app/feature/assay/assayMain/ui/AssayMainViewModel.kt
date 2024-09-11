package com.decide.app.feature.assay.assayMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayMain.data.AssayMainRepository
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssayMainViewModel @Inject constructor(
    repository: AssayMainRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AssayMainState())
    val state: StateFlow<AssayMainState> = _state.asStateFlow()

    private val assaysFlow =
        repository.getAssays().stateIn(
            viewModelScope, SharingStarted.Lazily, Resource.Success(emptyList())
        )

    private var assays = emptyList<AssayUI>()

    init {
        viewModelScope.launch {
            getAssays()
        }
    }


    private suspend fun getAssays() {
        assaysFlow.collect { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update { state ->
                        state.copy(
                            uiState = UIState.ERROR,
                        )
                    }
                }

                is Resource.Success -> {
                    assays = result.data.map { it.toAssayUI() }
                    _state.update { state ->
                        state.copy(
                            uiState = UIState.SUCCESS,
                            assays = result.data.map { it.toAssayUI() }
                                .toImmutableList())
                    }

                }
            }
        }
    }


    fun onEvent(event: AssayMainEvent) {
        when (event) {
            is AssayMainEvent.SetSearch -> {
                _state.update { state ->
                    state.copy(
                        searchText = event.search,
                        assays = if (event.search.isBlank()) {
                            assays.toImmutableList()
                        } else {
                            assays.filter { it.name.contains(event.search, ignoreCase = true) }
                                .toImmutableList()
                        }
                    )
                }
            }
        }
    }
}