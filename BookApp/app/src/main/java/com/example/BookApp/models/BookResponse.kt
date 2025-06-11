package com.example.BookApp.models

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("data")
    val data: List<Book>
)