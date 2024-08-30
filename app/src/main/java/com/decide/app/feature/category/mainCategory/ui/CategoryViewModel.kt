package com.decide.app.feature.category.mainCategory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.category.mainCategory.data.CategoryRepository
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _state = repository.getCategories().map(::separationState)
        .stateIn(viewModelScope, SharingStarted.Lazily, CategoryState.Initial)
    val state: StateFlow<CategoryState> = _state


    private fun separationState(result: Resource<List<Category>>): CategoryState {
        return when (result) {
            is Resource.Error -> {
                CategoryState.Error(result.exception?.message ?: "")
            }

            is Resource.Success -> {
                CategoryState.Loaded(result.data.toImmutableList())
            }
        }
    }
}