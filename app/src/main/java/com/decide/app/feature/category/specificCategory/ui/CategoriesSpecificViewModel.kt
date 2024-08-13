package com.decide.app.feature.category.specificCategory.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.category.specificCategory.data.CategoriesSpecificRepository
import com.decide.app.utils.toAssayUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesSpecificViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CategoriesSpecificRepository
) : ViewModel() {

    private val categoryId: Int = checkNotNull(savedStateHandle["idCategory"])

    private val _state = MutableStateFlow(CategoriesSpecificState.Initial)
    val state: StateFlow<CategoriesSpecificState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                CategoriesSpecificState.Success(repository.fetchAssaysByIdCategory(categoryId).map {
                    it.toAssayUI()
                })

            }
        }
    }
}