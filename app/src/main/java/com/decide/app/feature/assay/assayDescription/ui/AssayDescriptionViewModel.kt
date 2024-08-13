package com.decide.app.feature.assay.assayDescription.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayDescription.ui.data.AssayDescriptionRepository
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
class AssayDescriptionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayDescriptionRepository
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle["idAssay"])

    private val _state: MutableStateFlow<AssayDescriptionState> =
        MutableStateFlow(AssayDescriptionState.Default)
    val state: StateFlow<AssayDescriptionState> = _state.asStateFlow()


    fun getId(id: Int) {
        if (id == -1) {
            _state.update { AssayDescriptionState.Error }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                when (val assay = repository.getAssay(id)) {
                    is Resource.Error -> {
                        _state.update {
                            AssayDescriptionState.Error
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            AssayDescriptionState.Success(assay.data)
                        }
                    }
                }
            }
        }
    }
}