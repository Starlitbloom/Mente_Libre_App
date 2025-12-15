package com.example.mente_libre_app.data.remote.dto.chat

data class ChatResponseDto(
    val messageId: Long?,
    val petResponse: String,
    val timestamp: String
)