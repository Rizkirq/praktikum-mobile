package com.example.BookApp.ui.screens.BookDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookDetailState = MutableStateFlow<BookDetailState>(BookDetailState.Loading)
    val bookDetailState: StateFlow<BookDetailState> = _bookDetailState

    fun fetchBookDetails(id: Int) {
        viewModelScope.launch {
            _bookDetailState.value = BookDetailState.Loading
            bookRepository.getBookById(id).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _bookDetailState.value = BookDetailState.Loading
                    is Result.Success -> _bookDetailState.value = BookDetailState.Success(result.data)
                    is Result.Error -> _bookDetailState.value = BookDetailState.Error(result.message)
                }
            }
        }
    }

}