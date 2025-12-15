package com.example.mente_libre_app.data.remote.dto.emotion

data class MoodRecordDto(
    val id: Long? = null,
    val userId: String,
    val recordDate: String,
    val emotionLabel: String,
    val emotionScore: Int,
    val context: String?
)