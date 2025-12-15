package com.example.mente_libre_app.data.remote.dto.chat

data class ChatHistoryDto(
    val id: Long,
    val userMessage: String,
    val petResponse: String,
    val timestamp: String
)