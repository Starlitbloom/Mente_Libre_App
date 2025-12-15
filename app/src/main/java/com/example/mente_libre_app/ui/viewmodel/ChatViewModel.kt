package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.chat.ChatMessageDto
import com.example.mente_libre_app.data.remote.dto.chat.ChatRequestDto
import com.example.mente_libre_app.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repo: ChatRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val messages: StateFlow<List<ChatMessageDto>> = _messages

    fun sendMessage(userId: Long, petId: Long?, text: String) {
        viewModelScope.launch {

            try {
                val request = ChatRequestDto(userId, petId, text)

                val response = repo.sendMessage(request)

                if (response.isSuccessful) {
                    val msg = response.body()

                    if (msg != null) {
                        val uiMessage = ChatMessageDto(
                            id = msg.messageId ?: 0L,
                            userMessage = text,
                            petResponse = msg.petResponse,
                            timestamp = msg.timestamp
                        )

                        _messages.value = _messages.value + uiMessage
                    }
                } else {
                    println("Error HTTP: ${response.code()}")
                }

            } catch (e: Exception) {
                println("EXCEPCIÓN EN CHAT: ${e.message}")
            }
        }
    }

    fun loadHistory(userId: Long) {
        viewModelScope.launch {
            try {
                val response = repo.getHistory(userId)

                if (response.isSuccessful) {
                    val history = response.body()

                    if (history != null) {
                        val mapped = history.map { item ->
                            ChatMessageDto(
                                id = item.id,
                                userMessage = item.userMessage,
                                petResponse = item.petResponse,
                                timestamp = item.timestamp
                            )
                        }

                        _messages.value = mapped
                    }
                }

            } catch (e: Exception) {
                println("EXCEPCIÓN CARGANDO HISTORIAL: ${e.message}")
            }
        }
    }
}
