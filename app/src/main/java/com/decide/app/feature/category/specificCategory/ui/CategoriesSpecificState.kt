package com.decide.app.feature.category.specificCategory.ui

import com.decide.app.feature.assay.mainAssay.ui.AssayUI

sealed class CategoriesSpecificState {
    data class Success(val assays: List<AssayUI>) : CategoriesSpecificState()

    data object Default : CategoriesSpecificState()

    class Error(val message: String) : CategoriesSpecificState()

    companion object {
        val Initial: CategoriesSpecificState = Default
    }
}