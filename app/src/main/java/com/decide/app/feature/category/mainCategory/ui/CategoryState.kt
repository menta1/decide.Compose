package com.decide.app.feature.category.mainCategory.ui

import com.decide.app.feature.category.mainCategory.modals.Category

sealed interface CategoryState {

    data class Loaded(val categories: List<Category>) : CategoryState

    data object Loading : CategoryState
    data object Empty : CategoryState

    data object Error : CategoryState

    companion object {
        val Initial: CategoryState = Loading
    }
}