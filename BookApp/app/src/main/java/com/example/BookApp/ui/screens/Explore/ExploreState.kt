package com.example.BookApp.ui.screens.Explore

import com.example.BookApp.models.Book

sealed class ExploreState {
    object Loading : ExploreState()
    data class Success(val books: List<Book>) : ExploreState()
    data class Error(val message: String) : ExploreState()
}
