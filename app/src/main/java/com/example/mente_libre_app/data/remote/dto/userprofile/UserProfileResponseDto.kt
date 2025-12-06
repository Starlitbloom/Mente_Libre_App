package com.example.mente_libre_app.data.remote.dto.userprofile

data class UserProfileResponseDto(
    val id: Long,
    val userId: Long,
    val fotoPerfil: String?,
    val fechaNacimiento: String,
    val direccion: String,
    val notificaciones: Boolean,
    val genero: GeneroDto,
    val objetivo: String?,
    val tema: String?,
    val huellaActiva: Boolean
)

data class GeneroDto(
    val id: Long,
    val nombre: String
)
