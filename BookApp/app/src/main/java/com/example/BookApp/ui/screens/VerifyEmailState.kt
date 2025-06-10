package com.example.BookApp.ui.screens

import com.example.BookApp.models.User

sealed class VerifyEmailState {
    object Idle : VerifyEmailState()
    object Loading : VerifyEmailState()
    data class Success(val user: User, val message: String) : VerifyEmailState()
    data class ResendSuccess(val message: String) : VerifyEmailState()
    data class Error(val message: String) : VerifyEmailState()
}