package com.example.BookApp.ui.screens.home

import com.example.BookApp.models.Book

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val recommendedBooks: List<Book>, val updatedBooks: List<Book>) : HomeState()
    data class Error(val message: String) : HomeState()
}