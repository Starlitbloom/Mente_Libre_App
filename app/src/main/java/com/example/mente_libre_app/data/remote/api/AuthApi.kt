package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.AuthUserDto
import com.example.mente_libre_app.data.remote.dto.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @POST("/auth/api/v1/auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("/auth/api/v1/users")
    suspend fun register(@Body request: RegisterRequestDto)

    @GET("/auth/api/v1/users/{userId}")
    suspend fun getUserById(@Path("userId") userId: Long): AuthUserDto
}