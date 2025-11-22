package com.example.mente_libre_app.data.remote.dto

data class CreateUserProfileRequestDto(
    val userId: Long,
    val direccion: String,
    val fechaNacimiento: String,
    val notificaciones: Boolean,
    val generoId: Long,
    val fotoPerfil: String? = null // <-- nuevo campo
)
