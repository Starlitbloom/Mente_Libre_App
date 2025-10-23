package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.local.storage.MascotaPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MascotaViewModel : ViewModel() {

    private val _mascotaElegida = MutableStateFlow<String?>(null)
    val mascotaElegida: StateFlow<String?> = _mascotaElegida
    private val _nombreMascota = MutableStateFlow<String?>(null)
    val nombreMascota: StateFlow<String?> = _nombreMascota

    // Cargar la mascota guardada
    fun cargarMascota(context: Context) {
        viewModelScope.launch {
            MascotaPreferences.obtenerMascota(context).collect {
                _mascotaElegida.value = it
            }
        }
    }

    // Guardar una nueva mascota
    fun guardarMascota(context: Context, mascota: String) {
        viewModelScope.launch {
            MascotaPreferences.guardarMascota(context, mascota)
            _mascotaElegida.value = mascota
        }
    }

    fun guardarNombre(context: Context, nombre: String) {
        viewModelScope.launch {
            MascotaPreferences.guardarNombre(context, nombre)
            _nombreMascota.value = nombre
        }
    }

    fun cargarNombre(context: Context) {
        viewModelScope.launch {
            MascotaPreferences.obtenerNombre(context).collect {
                _nombreMascota.value = it
            }
        }
    }
}