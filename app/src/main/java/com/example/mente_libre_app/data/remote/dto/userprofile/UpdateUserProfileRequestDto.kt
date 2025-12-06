package com.example.mente_libre_app.data.remote.dto.userprofile

data class UpdateUserProfileRequestDto(
    val fotoPerfil: String? = null,
    val fechaNacimiento: String? = null,
    val direccion: String? = null,
    val notificaciones: Boolean? = null,
    val generoId: Long? = null,
    val objetivo: String? = null,
    val tema: String? = null,
    val huellaActiva: Boolean? = null
)

