package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.remote.api.ChatApi
import com.example.mente_libre_app.data.remote.dto.chat.ChatRequestDto

class ChatRepository(
    private val api: ChatApi
) {

    suspend fun sendMessage(dto: ChatRequestDto) =
        api.sendMessage(dto)

    suspend fun getHistory(userId: Long) =
        api.getHistory(userId)
}
