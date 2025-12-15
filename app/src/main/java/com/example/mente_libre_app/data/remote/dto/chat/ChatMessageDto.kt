package com.example.mente_libre_app.data.remote.dto.chat

data class ChatMessageDto(
    val id: Long,
    val userMessage: String? = null,
    val petResponse: String? = null,
    val timestamp: String
)
