package com.example.mente_libre_app.data.remote.dto.userprofile

data class CreateUserProfileRequestDto(
    val userId: Long,
    val fotoPerfil: String?,
    val fechaNacimiento: String,
    val direccion: String,
    val notificaciones: Boolean,
    val generoId: Long,
    val objetivo: String?,
    val tema: String?,
    val huellaActiva: Boolean
)
