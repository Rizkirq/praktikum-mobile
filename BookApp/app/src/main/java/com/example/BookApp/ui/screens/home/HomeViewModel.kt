package com.example.BookApp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.repository.BookRepository
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeState: StateFlow<HomeState> = _homeState

    private val _userName = MutableStateFlow<String>("Guest")
    val userName: StateFlow<String> = _userName

    init {
        fetchBooks()
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            authRepository.getLoggedInUser().collectLatest { user ->
                _userName.value = user?.name ?: "Guest"
            }
        }
    }

    fun fetchBooks() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
            bookRepository.getBooks().collectLatest { result ->
                when (result) {
                    is Result.Loading -> _homeState.value = HomeState.Loading
                    is Result.Success -> {
                        val allBooks = result.data
                        val recommendedBooks = allBooks.shuffled().take(4)
                        val updatedBooks = allBooks.sortedByDescending { it.updatedAt ?: "" }.take(4)

                        _homeState.value = HomeState.Success(recommendedBooks, updatedBooks)
                    }
                    is Result.Error -> _homeState.value = HomeState.Error(result.message)
                }
            }
        }
    }
}