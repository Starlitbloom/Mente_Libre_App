package com.example.mente_libre_app.data.remote.dto

data class UserProfileDto(
    val id: Long?,
    val userId: Long,
    val direccion: String,
    val fechaNacimiento: String, // usar String para LocalDate en JSON
    val notificaciones: Boolean,
    val generoId: Long,
    val generoNombre: String,
    val fotoPerfil: String? = null
)

