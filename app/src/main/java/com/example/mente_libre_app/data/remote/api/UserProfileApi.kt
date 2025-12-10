package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.*
import com.example.mente_libre_app.data.remote.dto.userprofile.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UserProfileResponseDto
import retrofit2.http.*

interface UserProfileApi {

    @POST("/user/api/v1/user-profile")
    suspend fun createProfile(
        @Body body: CreateUserProfileRequestDto
    ): UserProfileResponseDto

    @GET("/user/api/v1/user-profile/me")
    suspend fun getMyProfile(): UserProfileResponseDto

    @PUT("/user/api/v1/user-profile/me")
    suspend fun updateMyProfile(
        @Body body: UpdateUserProfileRequestDto
    ): UserProfileResponseDto

    @DELETE("/user" +
            "/api/v1/user-profile/me")
    suspend fun deleteMyProfile(): String
}
