package com.example.BookApp.ui.screens.Explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.models.Book
import com.example.BookApp.repository.BookRepository
import com.example.BookApp.repository.AuthRepository // <-- Import ini
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.asStateFlow // Untuk userName
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _exploreState = MutableStateFlow<ExploreState>(ExploreState.Loading)
    val exploreState: StateFlow<ExploreState> = _exploreState

    private val _userName = MutableStateFlow<String>("Guest")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        // TODO: Trigger search functionality based on query
    }


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
            bookRepository.getBooks().collectLatest { result ->
                when (result) {
                    is Result.Loading -> _exploreState.value = ExploreState.Loading
                    is Result.Success -> _exploreState.value = ExploreState.Success(result.data)
                    is Result.Error -> _exploreState.value = ExploreState.Error(result.message)
                }
            }
        }
    }
}