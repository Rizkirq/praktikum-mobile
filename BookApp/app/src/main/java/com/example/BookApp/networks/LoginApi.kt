package com.example.BookApp.networks

import com.example.BookApp.models.LoginRequest
import com.example.BookApp.models.LoginResponse
import com.example.BookApp.models.RegisterRequest
import com.example.BookApp.models.ResendCodeRequest
import com.example.BookApp.models.VerifyEmailRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponse


    @POST("verify-email") // <-- Endpoint baru untuk verifikasi email
    suspend fun verifyEmail(@Body verifyEmailRequest: VerifyEmailRequest): LoginResponse

    @POST("resend-verification-code") // <-- Endpoint baru untuk kirim ulang kode
    suspend fun resendVerificationCode(@Body resendCodeRequest: ResendCodeRequest): LoginResponse
}