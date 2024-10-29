package com.decide.app.feature.assay.assayDescription.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayDescription.data.AssayDescriptionRepository
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssayDescriptionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayDescriptionRepository
) : ViewModel() {

    private val userId: Int = savedStateHandle["idAssay"] ?: -1

    init {
        getDescription()
    }

    private val _state =
        MutableStateFlow(AssayDescriptionState.Initial)
    val state = _state.asStateFlow()


    private fun getDescription() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val assay = repository.getAssay(userId)) {
                is Resource.Error -> {
                    _state.update {
                        AssayDescriptionState.Error
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        AssayDescriptionState.Loaded(
                            description = assay.data.description,
                            typeAssay = assay.data.type
                        )
                    }
                }
            }
        }
    }
}