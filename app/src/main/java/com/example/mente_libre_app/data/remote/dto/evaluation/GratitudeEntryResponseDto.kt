package com.example.mente_libre_app.data.remote.dto.evaluation

data class GratitudeEntryResponseDto(
    val id: Long?,
    val userId: String?,
    val date: String,
    val moodLabel: String?,
    val title: String?,
    val text: String,
    val createdAt: String?,
    val updatedAt: String?
)
