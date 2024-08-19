package com.decide.app.feature.assay.assayResult.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepository
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
class AssayWithResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayWithResultRepository
) : ViewModel() {

    private val idAssay: Int = savedStateHandle["idAssay"] ?: -1
    private val dateResult: String = savedStateHandle["dateAssay"] ?: "-1"

    private val _state = MutableStateFlow(AssayWithResultState.Initial)
    val state: StateFlow<AssayWithResultState> = _state.asStateFlow()

    init {
        getResult()
    }

    private fun getResult() {
        _state.update { AssayWithResultState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getResult(idAssay, dateResult.toLong())) {
                is Resource.Success -> {
                    _state.update {
                        AssayWithResultState.Loaded(
                            result = result.data.first,
                            shortResult = result.data.second
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        AssayWithResultState.Error
                    }
                }
            }
        }
    }
}