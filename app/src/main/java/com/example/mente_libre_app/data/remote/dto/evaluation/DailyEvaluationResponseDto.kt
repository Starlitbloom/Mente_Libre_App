package com.example.mente_libre_app.data.remote.dto.evaluation

data class DailyEvaluationResponseDto(
    val id: Long?,
    val userId: String?,
    val date: String,
    val mainMood: String?,
    val globalScore: Int?,
    val reflection: String?,
    val createdAt: String?,
    val updatedAt: String?
)
