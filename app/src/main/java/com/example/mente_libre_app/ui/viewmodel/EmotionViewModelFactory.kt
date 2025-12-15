package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.repository.EmotionRepository

class EmotionViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmotionViewModel::class.java)) {

            val api = RetrofitInstance.createEmotionApi(context)
            val repo = EmotionRepository(api)

            @Suppress("UNCHECKED_CAST")
            return EmotionViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}