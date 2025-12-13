package com.example.mente_libre_app.data.remote.dto.virtualpet

data class PetResponseDto(
    val id: Long,
    val userId: Long,
    val name: String,
    val type: String,
    val avatarKey: String,
    val points: Int,
    val level: Int,
    val experience: Int,
    val affinity: Int,
    val energy: Int,
)