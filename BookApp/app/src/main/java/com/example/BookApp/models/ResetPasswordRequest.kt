package com.example.BookApp.models

data class ResetPasswordRequest(
    val email: String,
    val token: String,
    val password: String,
    val password_confirmation: String
)