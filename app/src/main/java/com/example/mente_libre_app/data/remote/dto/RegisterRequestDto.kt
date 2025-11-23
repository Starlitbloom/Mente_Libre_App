package com.example.mente_libre_app.data.remote.dto

data class RegisterRequestDto(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)
