package com.decide.app.feature.category.mainCategory.ui

import com.decide.app.feature.category.mainCategory.modals.Category

sealed class CategoryState {

    data class Success(val categories: List<Category>): CategoryState()

    data object Default: CategoryState()

    class Error(val message: String): CategoryState()

    companion object{
        val Initial: CategoryState = Default
    }
}