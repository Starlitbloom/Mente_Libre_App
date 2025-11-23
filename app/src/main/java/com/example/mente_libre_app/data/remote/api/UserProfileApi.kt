package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.UserProfileDto
import retrofit2.http.*

interface UserProfileApi {

    @GET("/user-profile/api/v1/{userId}")
    suspend fun getProfileByUserId(@Path("userId") userId: Long): UserProfileDto

    @GET("/user-profile/api/v1/id/{id}")
    suspend fun getProfileById(@Path("id") id: Long): UserProfileDto

    @POST("/user-profile/api/v1")
    suspend fun createProfile(@Body request: CreateUserProfileRequestDto): UserProfileDto

    @PUT("/user-profile/api/v1/{id}")
    suspend fun updateProfile(@Path("id") id: Long, @Body request: CreateUserProfileRequestDto): UserProfileDto
}
