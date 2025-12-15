package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.repository.VirtualPetRepository


class PetViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {

            val api = RetrofitInstance.createVirtualPetApi(context)
            val repo = VirtualPetRepository(api)

            @Suppress("UNCHECKED_CAST")
            return PetViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
