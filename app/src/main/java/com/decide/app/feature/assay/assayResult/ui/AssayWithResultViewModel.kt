package com.decide.app.feature.assay.assayResult.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayResult.data.AssayWithResultRepository
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssayWithResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayWithResultRepository
) : ViewModel() {
    companion object {
        const val WITHOUT_DATE = "-1"
    }

    private val idAssay: Int = savedStateHandle["idAssay"] ?: -1
    private val dateResult: String = savedStateHandle["dateAssay"] ?: WITHOUT_DATE

    private val _state = MutableStateFlow(AssayWithResultState.Initial)
    val state = _state.asStateFlow()

    init {
        getResult()
    }

    private fun getResult() {
        Log.d("TAG", "idAssay = $idAssay")
        viewModelScope.launch(Dispatchers.IO) {
            if (dateResult != WITHOUT_DATE) {
                val result = repository.getResult(
                    id = idAssay,
                    dateResult = dateResult.toLong()
                ).collect {
                    determineState(it)
                }

            } else {
                val result = repository.getResult(
                    id = idAssay,
                ).collect {
                    determineState(it)
                }
            }

        }
    }

    private fun determineState(result: Resource<ResultCompletedAssay>) {
        when (result) {
            is Resource.Success -> {
                _state.update {
                    Log.d("TAG", "WithResult = ${result.data}")
                    AssayWithResultState.Loaded(result.data)
                }
            }

            is Resource.Error -> {
                _state.update {
                    Log.d("TAG", "WithResult = ${result.exception?.message}")
                    AssayWithResultState.Error
                }
            }
        }
    }


}