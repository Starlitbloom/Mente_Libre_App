package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.repository.UserRepository

class AuthViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {

            val tokenDataStore = TokenDataStore(context)
            val api = RetrofitInstance.createAuthApi(context)
            val repo = UserRepository(
                tokenDataStore = tokenDataStore,
                api = api
            )

            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
