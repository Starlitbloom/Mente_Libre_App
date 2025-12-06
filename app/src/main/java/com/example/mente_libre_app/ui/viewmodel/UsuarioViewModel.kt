package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.repository.UserProfileRepository
import com.example.mente_libre_app.data.remote.dto.userprofile.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UserProfileResponseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UserProfileRepository
) : ViewModel() {

    // --- UI STATE ---
    private val _perfil = MutableStateFlow<UserProfileResponseDto?>(null)
    val perfil: StateFlow<UserProfileResponseDto?> = _perfil

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _objetivo = MutableStateFlow<String?>(null)
    val objetivo: StateFlow<String?> = _objetivo

    private val _generoId = MutableStateFlow<Long?>(null)
    val generoId: StateFlow<Long?> = _generoId

    private val _generoNombre = MutableStateFlow<String?>(null)
    val generoNombre: StateFlow<String?> = _generoNombre

    private val _fotoPerfil = MutableStateFlow<String?>(null)
    val fotoPerfil: StateFlow<String?> = _fotoPerfil

    private val _tema = MutableStateFlow("Rosado")   // Valor por defecto
    val tema: StateFlow<String> = _tema

    // ============================================================
    // CARGAR PERFIL DESDE BACKEND
    fun cargarMiPerfil() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getMyProfile()
                _perfil.value = response
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al obtener perfil"
            }
            _loading.value = false
        }
    }

    // ============================================================
    // CREAR PERFIL
    fun crearPerfil(dto: CreateUserProfileRequestDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val resultado = repository.createProfile(dto)
                _perfil.value = resultado
                _error.value = null
                onResult(true)
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al crear perfil"
                onResult(false)
            }

            _loading.value = false
        }
    }

    // ============================================================
    // ACTUALIZAR PERFIL
    fun actualizarPerfil(dto: UpdateUserProfileRequestDto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val updated = repository.updateMyProfile(dto)
                _perfil.value = updated
                onResult(true)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al actualizar perfil"
                onResult(false)
            }

            _loading.value = false
        }
    }

    // ============================================================
    // ELIMINAR PERFIL
    fun eliminarPerfil(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteMyProfile()
                _perfil.value = null
                onResult(true)
            } catch (e: Exception) {
                _error.value = e.message
                onResult(false)
            }
        }
    }

    fun setObjetivo(valor: String) {
        _objetivo.value = valor
    }

    fun setGeneroId(id: Long, nombre: String) {
        _generoId.value = id
        _generoNombre.value = nombre
    }

    fun setFotoPerfil(uri: String) {
        _fotoPerfil.value = uri
    }

    fun setTema(nuevoTema: String) {
        _tema.value = nuevoTema
    }
}
