package com.example.mente_libre_app.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EvaluationViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EvaluationViewModel::class.java)) {
            return EvaluationViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
