package com.example.mente_libre_app.data.remote.dto.auth

data class ChangePasswordRequestDto(
    val oldPassword: String,
    val newPassword: String
)

