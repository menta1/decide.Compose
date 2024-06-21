package com.decide.app.utils

import com.decide.app.database.local.dto.AnswerAssayEntity
import com.decide.app.database.local.dto.AssayEntity
import com.decide.app.database.local.dto.QuestionAssayEntity
import com.decide.app.feature.assay.mainAssay.modals.AnswerAssay
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.app.feature.assay.mainAssay.ui.AssayUI

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}
