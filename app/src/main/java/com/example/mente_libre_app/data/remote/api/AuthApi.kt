package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}