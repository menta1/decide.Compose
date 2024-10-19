package com.decide.app.feature.passed.ui.showPassedResult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepository
import com.decide.app.utils.Constants.NO_ARGS_INT
import com.decide.app.utils.Constants.NO_ARGS_STR
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowPassedResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayWithResultRepository
) : ViewModel() {

    private val idAssay: Int = savedStateHandle["idAssay"] ?: NO_ARGS_INT
    private val dateResult: String = savedStateHandle["dateAssay"] ?: NO_ARGS_STR

    private val _state = MutableStateFlow(ShowPassedResultState.Initial)
    val state = _state.asStateFlow()

    init {
        if (idAssay != NO_ARGS_INT && dateResult != NO_ARGS_STR) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getResult(id = idAssay, dateResult = dateResult.toLong())
                    .collect { result ->
                        when (result) {
                            is Resource.Error -> _state.update { ShowPassedResultState.Error }
                            is Resource.Success -> _state.update {
                                ShowPassedResultState.Success(
                                    result = result.data,
                                    nameAssay = repository.getAssay(idAssay).name
                                )
                            }
                        }
                    }
            }
        } else {
            _state.update { ShowPassedResultState.Error }
        }

    }

}