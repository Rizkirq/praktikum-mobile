package com.example.BookApp.models

data class CheckResetTokenResponse(
    val valid: Boolean, // Menunjukkan apakah token valid atau tidak
    val message: String
)