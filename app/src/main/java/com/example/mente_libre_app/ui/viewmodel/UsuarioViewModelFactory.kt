package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.repository.UserProfileRepository

class UsuarioViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {

            val tokenDataStore = TokenDataStore(context)
            val api = RetrofitInstance.createUserProfileApi(context)
            val repo = UserProfileRepository(api)

            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
