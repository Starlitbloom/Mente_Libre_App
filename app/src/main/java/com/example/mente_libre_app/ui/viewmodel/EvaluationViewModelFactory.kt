package com.example.mente_libre_app.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.remote.repository.EvaluationRepository

class EvaluationViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EvaluationViewModel::class.java)) {
            // 1) Crear el repositorio usando el context
            val repo = EvaluationRepository(context)

            // 2) Pasar el repo al ViewModel
            @Suppress("UNCHECKED_CAST")
            return EvaluationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

