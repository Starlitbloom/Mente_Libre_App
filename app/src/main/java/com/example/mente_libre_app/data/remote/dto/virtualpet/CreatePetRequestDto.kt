package com.example.mente_libre_app.data.remote.dto.virtualpet

data class CreatePetRequestDto(
    val name: String,
    val type: String,
    val avatarKey: String
)