package com.decide.app.feature.assay.mainAssay.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.mainAssay.data.AssayMainRepository
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssayMainViewModel @Inject constructor(
    private val repository: AssayMainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<StateAssay> = MutableStateFlow(StateAssay.None)
    val state: StateFlow<StateAssay> = _state.asStateFlow()

    private val temp: MutableList<Assay> = mutableListOf()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAssays(
                onSuccess = { list ->

                    list.forEach {
                        temp.add(it)
                    }

                    _state.update {
                        StateAssay.Success(
                            list.map { assay -> assay.toAssayUI() }.toImmutableList()
                        )
                    }
                },
                onError = { exception ->
                    _state.update {
                        StateAssay.Error(exception)
                    }
                })
        }
    }

    fun onSuccess(data: List<Assay>) {

    }

    fun onEvent() {
        state.value
    }

}