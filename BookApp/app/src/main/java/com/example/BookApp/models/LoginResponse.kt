package com.example.BookApp.models

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult?
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)