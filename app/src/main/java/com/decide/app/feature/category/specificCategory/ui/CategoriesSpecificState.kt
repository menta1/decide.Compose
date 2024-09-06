package com.decide.app.feature.category.specificCategory.ui

import com.decide.app.feature.assay.assayMain.ui.AssayUI

sealed interface CategoriesSpecificState {

    data class Loaded(
        val assays: List<AssayUI>,
        val description: String
    ) : CategoriesSpecificState

    data object Loading : CategoriesSpecificState

    class Error(val message: String) : CategoriesSpecificState

    companion object {
        val Initial: CategoriesSpecificState = Loading
    }
}