package com.decide.app.feature.category.mainCategory.data

import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<Resource<List<Category>, DecideException>>
}