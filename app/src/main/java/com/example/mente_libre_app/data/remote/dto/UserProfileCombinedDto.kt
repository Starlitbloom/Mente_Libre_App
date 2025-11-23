package com.example.mente_libre_app.data.remote.dto

data class UserProfileCombinedDto(
    val id: Long?,
    val userId: Long,
    val username: String,
    val email: String,
    val phone: String,
    val direccion: String,
    val fechaNacimiento: String,
    val notificaciones: Boolean,
    val generoId: Long,
    val generoNombre: String,
    val fotoPerfil: String?
)