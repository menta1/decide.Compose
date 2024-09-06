package com.decide.app.utils

//sealed class Resource<out T> {
//    data class Success<T>(val data: T) : Resource<T>()
//    data class Error(val exception: Throwable? = null) : Resource<Nothing>()
//}
sealed interface Resource<T, E> {
    class Success<T, E>(val data: T) : Resource<T, E>
    class Error<T, E>(val error: E) : Resource<T, E>
}