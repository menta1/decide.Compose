package com.decide.app.feature.category.mainCategory.data

import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.category.mainCategory.modals.Category

interface CategoryRepository {
    suspend fun getCategories(
        onSuccess: (List<Category>) -> Unit,
        onError: (message: String) -> Unit
    )
}