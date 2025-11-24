package com.example.mente_libre_app.data.remote.dto.evaluation

data class GratitudeEntryRequestDto(
    val date: String,        // "2025-11-21"
    val moodLabel: String?,  // puede ser null
    val title: String?,      // opcional
    val text: String         // texto obligatorio
)
