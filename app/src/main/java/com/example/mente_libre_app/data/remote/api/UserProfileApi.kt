package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.UserProfileDto
import retrofit2.http.*

interface UserProfileApi {

    @GET("/user/api/v1/usuario_perfil/perfiles/user/{userId}")
    suspend fun getProfileByUserId(
        @Path("userId") userId: Long
    ): UserProfileDto

    @GET("/user/api/v1/usuario_perfil/{id}")
    suspend fun getProfileById(@Path("id") id: Long): UserProfileDto

    @POST("/user/api/v1/usuario_perfil/perfiles")
    suspend fun createProfile(@Body request: CreateUserProfileRequestDto): UserProfileDto

    @PUT("/user/api/v1/usuario_perfil/{id}")
    suspend fun updateProfile(@Path("id") id: Long, @Body request: CreateUserProfileRequestDto): UserProfileDto
}
