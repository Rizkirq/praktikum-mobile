package com.example.BookApp.helper

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T, val message: String? = null) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}