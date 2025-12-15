package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.chat.ChatRequestDto
import com.example.mente_libre_app.data.remote.dto.chat.ChatResponseDto
import com.example.mente_libre_app.data.remote.dto.chat.ChatHistoryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApi {

    @POST("chat/send")
    suspend fun sendMessage(
        @Body dto: ChatRequestDto
    ): Response<ChatResponseDto>

    @GET("chat/history/{userId}")
    suspend fun getHistory(
        @Path("userId") userId: Long
    ): Response<List<ChatHistoryDto>>
}
