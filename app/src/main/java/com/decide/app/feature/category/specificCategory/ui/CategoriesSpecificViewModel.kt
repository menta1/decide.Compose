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

    private val categoryId: Int = savedStateHandle["idCategory"] ?: EMPTY_CATEGORY_ID

    private val _state = MutableStateFlow(CategoriesSpecificState.Initial)
    val state: StateFlow<CategoriesSpecificState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (categoryId != EMPTY_CATEGORY_ID) {
                val assays = repository.fetchAssaysByIdCategory(categoryId).map {
                    it.toAssayUI()
                }
                val description = repository.getCategoryDescription(categoryId)
                _state.update {
                    CategoriesSpecificState.Loaded(assays, description)
                }
            } else {
                _state.update {
                    CategoriesSpecificState.Error("")
                }
            }
        }
    }

    companion object {
        const val EMPTY_CATEGORY_ID = -1
    }
}