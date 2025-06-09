package com.example.BookApp.networks

import com.example.BookApp.models.LoginRequest
import com.example.BookApp.models.LoginResponse
import com.example.BookApp.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponse

}