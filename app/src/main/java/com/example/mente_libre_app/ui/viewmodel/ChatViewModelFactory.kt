package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.repository.ChatRepository

class ChatViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {

            val api = RetrofitInstance.createChatApi(context)
            val repo = ChatRepository(api)

            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
