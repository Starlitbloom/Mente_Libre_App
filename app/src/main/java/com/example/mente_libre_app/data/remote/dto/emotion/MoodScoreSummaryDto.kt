package com.example.mente_libre_app.data.remote.dto.emotion

data class MoodScoreSummaryDto(
    val averageScore: Int,
    val healthMessage: String,
    val records: List<MoodRecordDto>
)