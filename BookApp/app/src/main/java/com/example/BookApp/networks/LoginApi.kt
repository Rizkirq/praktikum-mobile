package com.example.BookApp.networks

import com.example.BookApp.models.LoginRequest
import com.example.BookApp.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}