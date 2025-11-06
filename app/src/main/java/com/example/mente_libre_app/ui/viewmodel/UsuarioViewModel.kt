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
    fun fotoUsuarioFlow(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerFoto(context)
    }

    fun obtenerFoto(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerFoto(context)
    }

    fun guardarNombre(context: Context, nombre: String) {
        viewModelScope.launch {
            UsuarioPreferences.guardarNombre(context, nombre)
        }
    }

    fun obtenerNombre(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerNombre(context)
    }

    fun guardarEmail(context: Context, email: String) {
        viewModelScope.launch { UsuarioPreferences.guardarEmail(context, email) }
    }

    fun obtenerEmail(context: Context): Flow<String?> = UsuarioPreferences.obtenerEmail(context)

    fun guardarTelefono(context: Context, telefono: String) {
        viewModelScope.launch { UsuarioPreferences.guardarTelefono(context, telefono) }
    }

    fun obtenerTelefono(context: Context): Flow<String?> = UsuarioPreferences.obtenerTelefono(context)

    fun guardarCumpleanos(context: Context, fecha: String) {
        viewModelScope.launch {
            UsuarioPreferences.guardarCumpleanos(context, fecha)
        }
    }

    fun obtenerCumpleanos(context: Context): Flow<String?> {
        return UsuarioPreferences.obtenerCumpleanos(context)
    }

    fun guardarUbicacion(context: Context, latitud: Double, longitud: Double) {
        viewModelScope.launch {
            UsuarioPreferences.guardarUbicacion(context, latitud, longitud)
        }
    }

    fun obtenerUbicacion(context: Context): Flow<Pair<Double?, Double?>> {
        return UsuarioPreferences.obtenerUbicacion(context)
    }

}