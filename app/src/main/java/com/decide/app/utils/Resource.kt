package com.decide.app.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable? = null) : Resource<Nothing>()
}
