package com.example.BookApp.ui.screens.BookDetail

import com.example.BookApp.models.Book

sealed class BookDetailState {
    object Loading : BookDetailState()
    data class Success(val book: Book) : BookDetailState()
    data class Error(val message: String) : BookDetailState()
}