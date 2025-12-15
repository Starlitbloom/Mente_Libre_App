package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.repository.EmotionRepository
import com.example.mente_libre_app.data.remote.dto.emotion.MoodRecordDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmotionViewModel(
    private val repo: EmotionRepository
) : ViewModel() {

    private val _monthRecords = MutableStateFlow<List<MoodRecordDto>>(emptyList())
    val monthRecords: StateFlow<List<MoodRecordDto>> = _monthRecords

    fun loadMonth(userId: Long, year: Int, month: Int) {
        viewModelScope.launch {
            val res = repo.getMonth(userId, year, month)
            if (res.isSuccessful) {
                _monthRecords.value = res.body() ?: emptyList()
            }
        }
    }

    fun registerMood(userId: Long, mood: String, context: String? = null) {
        viewModelScope.launch {
            repo.registerMood(userId, mood, context)
        }
    }
}