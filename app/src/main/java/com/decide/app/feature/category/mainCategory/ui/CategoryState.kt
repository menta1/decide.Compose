package com.decide.app.feature.category.mainCategory.ui

import com.decide.app.feature.category.mainCategory.modals.Category

sealed interface CategoryState {

    data class Success(val categories: List<Category>) : CategoryState

    data object Loading : CategoryState

    class Error(val message: String) : CategoryState

    companion object {
        val Initial: CategoryState = Loading
    }
}