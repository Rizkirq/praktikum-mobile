package com.example.BookApp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val name: String,
    val token: String
)
