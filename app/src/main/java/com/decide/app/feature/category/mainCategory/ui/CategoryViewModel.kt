package com.decide.app.feature.category.mainCategory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.category.mainCategory.data.CategoryRepository
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    private var nowDownloadAttempts = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCategories()
        }
    }

    private suspend fun getCategories() {
        when (val result = repository.getCategories()) {
            is Resource.Error -> {
                when (result.error) {
                    is DecideException.NoFindElementDB -> {
                        if (nowDownloadAttempts != DOWNLOAD_ATTEMPTS) {
                            delay(500)
                            getCategories()
                            nowDownloadAttempts++
                        } else {
                            _state.update { CategoryState.Error }
                        }
                    }

                    else -> {
                        _state.update { CategoryState.Error }
                    }
                }
            }

            is Resource.Success -> {
                _state.update {
                    CategoryState.Loaded(result.data.filter { it.countAssays != 0 })
                }
            }
        }
    }


    companion object {
        const val DOWNLOAD_ATTEMPTS = 3
    }
}