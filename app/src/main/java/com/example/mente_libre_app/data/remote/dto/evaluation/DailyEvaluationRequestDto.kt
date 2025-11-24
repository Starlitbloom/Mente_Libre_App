package com.example.mente_libre_app.data.remote.dto.evaluation

data class DailyEvaluationRequestDto(
    val date: String,          // yyyy-MM-dd
    val moodLabel: String,     // "FELIZ", "TRISTE"...
    val globalScore: Int?,     // puede ser null
    val reflection: String?    // puede ser null
)
