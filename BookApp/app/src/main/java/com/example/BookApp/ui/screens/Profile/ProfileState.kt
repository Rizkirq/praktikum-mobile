package com.example.BookApp.ui.screens.Profile

import com.example.BookApp.models.User

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}