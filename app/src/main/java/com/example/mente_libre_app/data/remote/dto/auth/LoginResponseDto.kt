package com.example.mente_libre_app.data.remote.dto.auth

data class LoginResponseDto(
    val token: String,
    val userId: Long,
    val username: String,
    val email: String,
    val phone: String,
    val role: String
)