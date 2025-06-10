package com.example.BookApp.models

data class VerifyEmailRequest(
    val email: String,
    val verification_code: String // Sesuaikan dengan nama field di Laravel
)