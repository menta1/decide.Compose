package com.decide.app.feature.category.mainCategory.ui

import androidx.compose.runtime.Immutable
import com.decide.app.feature.category.mainCategory.modals.Category

sealed interface CategoryState {

    @Immutable
    data class Loaded(val categories: List<Category>) : CategoryState

    data object Loading : CategoryState
    data object Empty : CategoryState

    class Error(val message: String) : CategoryState

    companion object {
        val Initial: CategoryState = Loading
    }
}