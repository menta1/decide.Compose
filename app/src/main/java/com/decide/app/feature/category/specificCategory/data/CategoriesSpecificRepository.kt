package com.decide.app.feature.category.specificCategory.data

import com.decide.app.feature.assay.mainAssay.modals.Assay

interface CategoriesSpecificRepository {
    suspend fun fetchAssaysByIdCategory(idCategory: Int): List<Assay>
    suspend fun getCategoryDescription(idCategory: Int): String
}