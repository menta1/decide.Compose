package com.decide.app.feature.category.mainCategory.data

import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource

interface CategoryRepository {
    suspend fun getCategories(): Resource<List<Category>, DecideException>
}