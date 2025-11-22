package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UserProfileRepository
) : ViewModel() {

    // --- Campos del perfil ---
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono

    private val _direccion = MutableStateFlow("")
    val direccion: StateFlow<String> = _direccion

    private val _cumpleanos = MutableStateFlow("")
    val cumpleanos: StateFlow<String> = _cumpleanos

    private val _generoId = MutableStateFlow(0L)
    val generoId: StateFlow<Long> = _generoId

    private val _generoNombre = MutableStateFlow("No especificado")
    val generoNombre: StateFlow<String> = _generoNombre

    private val _ubicacion = MutableStateFlow<Pair<Double, Double>?>(null)
    val ubicacion: StateFlow<Pair<Double, Double>?> = _ubicacion

    private val _fotoPerfil = MutableStateFlow<String?>(null)
    val fotoPerfil: StateFlow<String?> = _fotoPerfil

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    // --- Funciones para actualizar campos locales ---
    fun setFotoPerfil(uri: String?) { _fotoPerfil.value = uri }
    fun setCumpleanos(valor: String) { _cumpleanos.value = valor }
    fun setGeneroId(id: Long, nombre: String) {
        _generoId.value = id
        _generoNombre.value = nombre
    }
    fun setDireccion(valor: String) { _direccion.value = valor }
    fun setUbicacion(lat: Double, lon: Double) { _ubicacion.value = Pair(lat, lon) }
    fun setUsername(valor: String) { _nombre.value = valor }
    fun setEmail(valor: String) { _email.value = valor }
    fun setPhone(valor: String) { _telefono.value = valor }

    // --- Cargar perfil desde backend ---
    fun cargarPerfil(userId: Long) {
        viewModelScope.launch {
            val result = repository.getFullUserProfile(userId)
            result.fold(
                onSuccess = { perfil ->
                    _nombre.value = perfil.username
                    _email.value = perfil.email
                    _telefono.value = perfil.phone
                    _direccion.value = perfil.direccion
                    _cumpleanos.value = perfil.fechaNacimiento
                    _generoId.value = perfil.generoId
                    _generoNombre.value = perfil.generoNombre
                    _fotoPerfil.value = perfil.fotoPerfil
                    _errorMsg.value = null
                },
                onFailure = { e ->
                    _errorMsg.value = e.message ?: "Error al cargar perfil"
                }
            )
        }
    }

    // --- Crear o actualizar perfil ---
    fun actualizarPerfil(
        userId: Long,
        direccion: String,
        fechaNacimiento: String,
        generoId: Long,
        fotoPerfil: String?, // opcional
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val request = CreateUserProfileRequestDto(
                userId = userId,
                direccion = direccion,
                fechaNacimiento = fechaNacimiento,
                notificaciones = false,
                generoId = generoId,
                fotoPerfil = fotoPerfil
            )
            val result = repository.createProfile(request)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { e ->
                    _errorMsg.value = e.message ?: "Error al actualizar perfil"
                    onResult(false)
                }
            )
        }
    }

    // --- Campo temporal para objetivo ---
    private val _objetivoSeleccionado = MutableStateFlow<String?>(null)
    val objetivoSeleccionado: StateFlow<String?> = _objetivoSeleccionado

    fun setObjetivo(objetivo: String) { _objetivoSeleccionado.value = objetivo }
}
