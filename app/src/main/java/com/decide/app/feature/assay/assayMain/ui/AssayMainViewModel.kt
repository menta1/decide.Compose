package com.decide.app.feature.assay.assayMain.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayMain.data.AssayMainRepository
import com.decide.app.utils.Resource
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
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
    private val repository: AssayMainRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AssayMainState())
    val state: StateFlow<AssayMainState> = _state.asStateFlow()
    private var lastScrollIndex = 0

    private val assaysFlow = repository.getAssays().stateIn(
        viewModelScope, SharingStarted.Lazily, Resource.Success(emptyList())
    )

    private var assays = emptyList<AssayUI>()

    init {
        viewModelScope.launch {
            getAssays()
        }
    }


    private suspend fun getAssays() {
        repository.getAssays().collect { result ->
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
                    if (assays.isEmpty()) {
                        _state.update { state ->
                            state.copy(
                                uiState = UIState.LOADING
                            )
                        }
                    } else {
                        _state.update { state ->
                            state.copy(uiState = UIState.SUCCESS,
                                assays = result.data.map { it.toAssayUI() })
                        }
                    }
                }
            }
        }
    }


    fun onEvent(event: AssayMainEvent) {
        when (event) {
            is AssayMainEvent.SetSearch -> {
                _state.update { state ->
                    state.copy(searchText = event.search, assays = if (event.search.isBlank()) {
                        assays.toImmutableList()
                    } else {
                        assays.filter { it.name.contains(event.search, ignoreCase = true) }
                            .toImmutableList()
                    })
                }
            }

            is AssayMainEvent.Update -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getAssays()
                }
            }

            is AssayMainEvent.ScrollState -> {
                if (event.newScrollIndex == lastScrollIndex) return

                _state.update { state ->
                    state.copy(scrollUp = event.newScrollIndex > lastScrollIndex)
                }
                lastScrollIndex = event.newScrollIndex

            }
        }
    }
}