package com.takeshi.gouda

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : com.takeshi.gouda.Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : com.takeshi.gouda.Result<Nothing>()
}