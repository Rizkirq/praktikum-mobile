package com.example.BookApp.ui.screens.Library

import com.example.BookApp.models.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : androidx.lifecycle.ViewModel() {
    private val _userName = MutableStateFlow<String>("Sugeng")
    val userName: StateFlow<String> = _userName.asStateFlow()

    val bookmarkedBooks: StateFlow<List<Book>> = MutableStateFlow<List<Book>>(emptyList()).asStateFlow()
    val historyBooks: StateFlow<List<Book>> = MutableStateFlow<List<Book>>(emptyList()).asStateFlow()
}