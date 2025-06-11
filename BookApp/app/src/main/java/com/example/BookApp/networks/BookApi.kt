package com.example.BookApp.networks

import com.example.BookApp.models.Book
import com.example.BookApp.models.BookResponse
import com.example.BookApp.models.BookSingleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("buku")
    suspend fun getBooks(): BookResponse

    @GET("buku/{id}")
    suspend fun getBookById(@Path("id") bookId: Int): BookSingleResponse

}