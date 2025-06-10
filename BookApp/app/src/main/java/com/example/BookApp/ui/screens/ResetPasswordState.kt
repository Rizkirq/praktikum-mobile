package com.example.BookApp.ui.screens

sealed class ResetPasswordState {
    object Idle : ResetPasswordState()
    object Loading : ResetPasswordState()
    data class Success(val message: String) : ResetPasswordState()
    data class Error(val message: String) : ResetPasswordState()
}