package com.example.mente_libre_app.data.remote.dto.auth

data class UserResponseDto(
    val id: Long,
    val username: String,
    val email: String,
    val phone: String,
    val rol: String
)
