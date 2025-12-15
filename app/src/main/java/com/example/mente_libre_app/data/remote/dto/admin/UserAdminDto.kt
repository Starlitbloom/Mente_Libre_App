package com.example.mente_libre_app.data.remote.dto.admin

data class UserAdminDto(
    val id: Long,
    val username: String,
    val email: String,
    val bloqueado: Boolean,
    val rol: RolDto?
)