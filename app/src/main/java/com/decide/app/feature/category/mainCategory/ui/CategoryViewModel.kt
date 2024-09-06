package com.decide.app.feature.category.mainCategory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.category.mainCategory.data.CategoryRepository
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.DecideException
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


    private fun separationState(result: Resource<List<Category>, DecideException>): CategoryState {
        return when (result) {
            is Resource.Success -> {
                CategoryState.Loaded(result.data.toImmutableList())
            }

            is Resource.Error -> {
                when (result.error) {
                    is DecideException.NoFindElementDB -> {
                        CategoryState.Empty
                    }

                    else -> {
                        CategoryState.Error(result.error.messageLog)
                    }
                }

            }
        }
    }
}