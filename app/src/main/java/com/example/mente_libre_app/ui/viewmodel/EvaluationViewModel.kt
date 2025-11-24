package com.example.mente_libre_app.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryRequestDto
import com.example.mente_libre_app.data.remote.repository.EvaluationRepository
import kotlinx.coroutines.launch

class EvaluationViewModel(
    private val repository: EvaluationRepository
) : ViewModel() {

    var isLoading: Boolean = false
        private set

    var errorMessage: String? = null
        private set

    fun saveGratitude(
        date: String,
        moodLabel: String?,
        title: String?,
        text: String
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val request = GratitudeEntryRequestDto(
                    date = date,
                    moodLabel = moodLabel,
                    title = title,
                    text = text
                )

                repository.saveGratitudeEntry(request)

            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
