package com.example.BookApp.ui.screens.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookApp.helper.Result
import com.example.BookApp.models.User
import com.example.BookApp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> = _profileState

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess
    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            authRepository.getLoggedInUser().collectLatest { user ->
                if (user != null) {
                    _profileState.value = ProfileState.Success(user)
                } else {
                    _profileState.value = ProfileState.Error("User not logged in or data not found.")
                }
            }
        }
    }
    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                _logoutSuccess.value = true
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(e.message ?: "Failed to logout.")
            }
        }
    }
}