package com.example.BookApp.models

import com.google.gson.annotations.SerializedName

data class BookSingleResponse(
    @SerializedName("data")
    val data: Book
)
