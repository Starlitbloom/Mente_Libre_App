package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.repository.UserProfileRepository
import com.example.mente_libre_app.data.remote.dto.userprofile.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UpdateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.userprofile.UserProfileResponseDto
import com.example.mente_libre_app.data.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UserProfileRepository,
    private val storageRepository: StorageRepository
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

    fun crearPerfilFinal(
        userId: Long,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val dto = CreateUserProfileRequestDto(
                    userId = userId,
                    fotoPerfil = _fotoPerfil.value,
                    fechaNacimiento = "",
                    direccion = "",
                    notificaciones = true,
                    generoId = _generoId.value ?: 3,
                    objetivo = _objetivo.value,
                    tema = _tema.value,
                    huellaActiva = true
                )

                val response = repository.createProfile(dto)
                _perfil.value = response

                onResult(true)
            } catch (e: Exception) {
                Log.e("Perfil", "Error al crear perfil", e)   //<--- AGRÉGALO
                _error.value = e.message ?: "Error al crear perfil"
                onResult(false) //<--- IMPORTANTE
            }

            _loading.value = false
        }
    }

    private val _direccion = MutableStateFlow("")
    val direccion: StateFlow<String> = _direccion

    fun setDireccion(nueva: String) {
        _direccion.value = nueva
    }

    private val _cumpleanos = MutableStateFlow("")
    val cumpleanos: StateFlow<String> = _cumpleanos

    fun setCumpleanos(nueva: String) {
        _cumpleanos.value = nueva
    }

    fun subirFoto(context: Context, uri: Uri, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = storageRepository.uploadProfileImage(context, uri)

                // Detectar si la app corre en EMULADOR o CELULAR REAL
                val baseUrl = if (android.os.Build.FINGERPRINT.contains("generic")) {
                    // ✔ Emulador
                    "http://10.0.2.2:8085"
                } else {
                    // ✔ Dispositivo físico (tu teléfono)
                    "http://192.168.1.105:8085"   // <-- ESTA ES TU IP
                }

                val fullUrl = baseUrl + result.url

                println("URL subida correctamente: $fullUrl")

                _fotoPerfil.value = fullUrl
                onResult(fullUrl)

            } catch (e: Exception) {
                println("Error subiendo foto: ${e.message}")
                onResult(null)
            }
        }
    }

}
