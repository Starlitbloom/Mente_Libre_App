package com.example.mente_libre_app.data.remote.dto.emotion

data class MoodScoreEntryDto(
    val date: String,
    val moodType: String?,
    val score: Int
)