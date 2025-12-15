package com.example.mente_libre_app.data.remote.dto.chat

data class ChatRequestDto(
    val userId: Long,
    val petId: Long?,
    val userMessage: String
)
