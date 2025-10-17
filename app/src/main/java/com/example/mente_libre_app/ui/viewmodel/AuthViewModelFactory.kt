package com.example.mente_libre_app.ui.viewmodel


import androidx.lifecycle.ViewModel                              // Tipo base ViewModel
import androidx.lifecycle.ViewModelProvider                      // Factory de ViewModels
import com.example.mente_libre_app.data.repository.UserRepository

// Factory simple para crear AuthViewModel con su UserRepository.
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

