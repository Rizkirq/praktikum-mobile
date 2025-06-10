package com.example.BookApp.ui.screens.LupaPassword

sealed class EnterResetCodeState {
    object Idle : EnterResetCodeState()
    object Loading : EnterResetCodeState()
    data class Success(val message: String) : EnterResetCodeState()
    data class ResendSuccess(val message: String) : EnterResetCodeState()
    data class Error(val message: String) : EnterResetCodeState()
}