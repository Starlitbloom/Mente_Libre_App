package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.auth.ChangePasswordRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.auth.RegisterRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import okhttp3.ResponseBody

interface AuthApi {

    @POST("auth/api/v1/auth/login")
    suspend fun login(@Body dto: LoginRequestDto): Response<LoginResponseDto>

    @POST("auth/api/v1/auth/register")
    suspend fun register(@Body dto: RegisterRequestDto): Response<UserResponseDto>

    @GET("auth/api/v1/auth/me")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserResponseDto>

    @PUT("auth/api/v1/auth/me/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body body: ChangePasswordRequestDto
    ): Response<ResponseBody>

    @DELETE("auth/api/v1/auth/me")
    suspend fun deleteMyAccount(@Header("Authorization") token: String): Response<ResponseBody>


}