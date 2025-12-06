package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.remote.api.UserProfileApi
import com.example.mente_libre_app.data.remote.dto.userprofile.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UserProfileResponseDto

class UserProfileRepository(
    private val api: UserProfileApi
) {

    suspend fun createProfile(dto: CreateUserProfileRequestDto): UserProfileResponseDto {
        return api.createProfile(dto)
    }

    suspend fun getMyProfile(): UserProfileResponseDto {
        return api.getMyProfile()
    }

    suspend fun updateMyProfile(dto: UpdateUserProfileRequestDto): UserProfileResponseDto {
        return api.updateMyProfile(dto)
    }

    suspend fun deleteMyProfile(): String {
        return api.deleteMyProfile()
    }
}
