package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.local.storage.UsuarioPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    // Guardar el género en DataStore
    fun guardarGenero(context: Context, genero: String) {
        viewModelScope.launch {
            UsuarioPreferences.guardarGenero(context, genero)
        }
    }

    // Obtener el género guardado como Flow
    fun obtenerGenero(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerGenero(context)
    }

    fun guardarObjetivo(context: Context, objetivo: String) {
        viewModelScope.launch {
            UsuarioPreferences.guardarObjetivo(context, objetivo)
        }
    }

    fun obtenerObjetivo(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerObjetivo(context)
    }

    fun guardarFoto(context: Context, uri: String) {
        viewModelScope.launch {
            UsuarioPreferences.guardarFoto(context, uri)
        }
    }

    fun obtenerFoto(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerFoto(context)
    }

}