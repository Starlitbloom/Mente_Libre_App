package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.auth.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.auth.RegisterRequestDto
import com.example.mente_libre_app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val pass: String = "",
    val emailError: String? = null,
    val passError: String? = null,
    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val pass: String = "",
    val confirm: String = "",

    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passError: String? = null,
    val confirmError: String? = null,

    val isSubmitting: Boolean = false,
    val canSubmit: Boolean = false,
    val success: Boolean = false,
    val errorMsg: String? = null
)


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _login = MutableStateFlow(LoginUiState())
    val login = _login.asStateFlow()

    private val _register = MutableStateFlow(RegisterUiState())
    val register = _register.asStateFlow()

    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId = _currentUserId.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole = _userRole.asStateFlow()

    private val _usuario = MutableStateFlow<LoginResponseDto?>(null)
    val usuario: StateFlow<LoginResponseDto?> = _usuario

    val isLoggedIn = currentUserId
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        viewModelScope.launch {
            _currentUserId.value = repository.getUserId()
        }
    }

    // ---- LOGIN ----
    fun onLoginEmailChange(v: String) {
        _login.update { it.copy(email = v) }
        recomputeLogin()
    }

    fun onLoginPassChange(v: String) {
        _login.update { it.copy(pass = v) }
        recomputeLogin()
    }

    private fun recomputeLogin() {
        val s = _login.value
        _login.update { it.copy(canSubmit = s.email.isNotBlank() && s.pass.isNotBlank()) }
    }

    fun submitLogin() {
        val data = _login.value
        if (!data.canSubmit || data.isSubmitting) return

        viewModelScope.launch {
            _login.update { it.copy(isSubmitting = true, errorMsg = null) }

            val result = repository.login(data.email, data.pass)
            result.fold(
                onSuccess = { response ->

                    repository.saveToken(response.token)

                    _usuario.value = response
                    _currentUserId.value = response.userId
                    _userRole.value = response.role

                    _login.update { it.copy(isSubmitting = false, success = true) }
                },
                onFailure = { e ->
                    _login.update { it.copy(isSubmitting = false, errorMsg = e.message) }
                }
            )
        }
    }

    fun onNameChange(v: String) {
        _register.update { it.copy(name = v) }
        recomputeRegister()
    }

    fun onPhoneChange(v: String) {
        _register.update { it.copy(phone = v.filter { it.isDigit() }) }
        recomputeRegister()
    }

    fun onEmailChange(v: String) {
        _register.update { it.copy(email = v) }
        recomputeRegister()
    }

    fun onPassChange(v: String) {
        _register.update { it.copy(pass = v) }
        recomputeRegister()
    }

    fun onConfirmChange(v: String) {
        _register.update { it.copy(confirm = v) }
        recomputeRegister()
    }

    // ---- RECOMPUTE REGISTER ----
    private fun recomputeRegister() {
        val s = _register.value

        val filled = s.name.isNotBlank() &&
                s.email.isNotBlank() &&
                s.phone.isNotBlank() &&
                s.pass.isNotBlank() &&
                s.confirm.isNotBlank()

        _register.update { it.copy(canSubmit = filled) }
    }

    // ---- REGISTER ----
    fun submitRegister() {
        val s = _register.value
        if (!s.canSubmit || s.isSubmitting) return

        viewModelScope.launch {
            _register.update { it.copy(isSubmitting = true, errorMsg = null) }

            val request = RegisterRequestDto(
                username = s.name,
                email = s.email,
                phone = s.phone,
                password = s.pass
            )

            repository.register(request).fold(
                onSuccess = { user ->

                    // Hacer login automático
                    val loginResult = repository.login(user.email, s.pass)

                    loginResult.fold(
                        onSuccess = { loginData ->

                            _usuario.value = loginData
                            // Guardar token Y userId
                            _currentUserId.value = loginData.userId
                            repository.saveToken(loginData.token)
                            _userRole.value = loginData.role
                            _register.update { it.copy(isSubmitting = false, success = true) }
                        },

                        onFailure = { err ->
                            _register.update {
                                it.copy(isSubmitting = false, errorMsg = "Error al iniciar sesión automáticamente: ${err.message}")
                            }
                        }
                    )
                },
                onFailure = { e ->
                    _register.update { it.copy(isSubmitting = false, errorMsg = e.message) }
                }
            )
        }
    }

    fun clearRegisterResult() {
        _register.update { it.copy(success = false, errorMsg = null) }
    }

    fun clearLoginResult() {
        _login.update { it.copy(success = false, errorMsg = null) }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _currentUserId.value = null
        }
    }

    fun setUsername(v: String) {
        _usuario.value = _usuario.value?.copy(username = v)
    }

    fun setEmail(v: String) {
        _usuario.value = _usuario.value?.copy(email = v)
    }

    fun setPhone(v: String) {
        _usuario.value = _usuario.value?.copy(phone = v)
    }

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    init {
        viewModelScope.launch {
            _token.value = repository.getToken()
        }
    }

    data class ChangePassResult(
        val success: Boolean,
        val errorMsg: String? = null
    )

    fun changePassword(
        actual: String,
        nueva: String,
        confirmar: String,
        onResult: (ChangePassResult) -> Unit
    ) {
        viewModelScope.launch {
            if (nueva != confirmar) {
                onResult(ChangePassResult(false, "Las contraseñas nuevas no coinciden"))
                return@launch
            }

            try {
                val result = repository.changePassword(actual, nueva)
                result.fold(
                    onSuccess = {
                        onResult(ChangePassResult(true))
                    },
                    onFailure = { e ->
                        onResult(ChangePassResult(false, e.message ?: "Error al cambiar contraseña"))
                    }
                )
            } catch (e: Exception) {
                onResult(ChangePassResult(false, e.message ?: "Error inesperado"))
            }
        }
    }

    fun deleteAuthAccount(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.deleteAuthAccount()
                result.fold(
                    onSuccess = { onResult(true) },
                    onFailure = { onResult(false) }
                )
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

}





