package com.decide.app.feature.category.mainCategory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.category.mainCategory.data.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState.Initial)
    val state: StateFlow<CategoryState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories(onSuccess = { result ->
                _state.update {
                    CategoryState.Success(result)
                }

            }, onError = { exception ->
                _state.update {
                    CategoryState.Error(exception)
                }
            })
        }
    }
}