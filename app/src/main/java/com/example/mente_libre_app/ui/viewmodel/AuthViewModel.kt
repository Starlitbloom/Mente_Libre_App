package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.RegisterRequestDto
import com.example.mente_libre_app.data.repository.UserRepository
import kotlinx.coroutines.flow.*
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
    private val repository: UserRepository,
    private val context: Context // ya no usamos preferences
) : ViewModel() {

    private val _login = MutableStateFlow(LoginUiState())
    val login: StateFlow<LoginUiState> = _login

    private val _register = MutableStateFlow(RegisterUiState())
    val register: StateFlow<RegisterUiState> = _register

    // --- Usuario logueado ---
    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId: StateFlow<Long?> = _currentUserId

    // isLoggedIn derivado de currentUserId
    val isLoggedIn: StateFlow<Boolean> = _currentUserId
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        viewModelScope.launch {
            _currentUserId.value = repository.getCurrentUserId()
        }
    }

    fun setCurrentUserId(id: Long) {
        _currentUserId.value = id
    }

    // --- LOGIN ---
    fun onLoginEmailChange(value: String) {
        _login.update { it.copy(email = value) }
        recomputeLoginCanSubmit()
    }

    fun onLoginPassChange(value: String) {
        _login.update { it.copy(pass = value) }
        recomputeLoginCanSubmit()
    }

    private fun recomputeLoginCanSubmit() {
        val s = _login.value
        val can = s.email.isNotBlank() && s.pass.isNotBlank()
        _login.update { it.copy(canSubmit = can) }
    }

    fun submitLogin() {
        val s = _login.value
        if (!s.canSubmit || s.isSubmitting) return

        viewModelScope.launch {
            _login.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            val result = repository.login(s.email.trim(), s.pass)
            result.fold(
                onSuccess = { response ->
                    _currentUserId.value = response.userId
                    _login.update { it.copy(isSubmitting = false, success = true, errorMsg = null) }
                },
                onFailure = { e ->
                    _login.update { it.copy(isSubmitting = false, success = false, errorMsg = e.message ?: "Error de login") }
                }
            )
        }
    }

    fun clearLoginResult() {
        _login.update { it.copy(success = false, errorMsg = null) }
    }

    // --- REGISTER ---
    fun onNameChange(value: String) {
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _register.update { it.copy(name = filtered) }
        recomputeRegisterCanSubmit()
    }

    fun onEmailChange(value: String) {
        _register.update { it.copy(email = value) }
        recomputeRegisterCanSubmit()
    }

    fun onPhoneChange(value: String) {
        val digitsOnly = value.filter { it.isDigit() }
        _register.update { it.copy(phone = digitsOnly) }
        recomputeRegisterCanSubmit()
    }

    fun onPassChange(value: String) {
        _register.update { it.copy(pass = value) }
        recomputeRegisterCanSubmit()
    }

    fun onConfirmChange(value: String) {
        _register.update { it.copy(confirm = value) }
        recomputeRegisterCanSubmit()
    }

    private fun recomputeRegisterCanSubmit() {
        val s = _register.value
        val filled = listOf(s.name, s.email, s.phone, s.pass, s.confirm).all { it.isNotBlank() }
        _register.update { it.copy(canSubmit = filled) }
    }

    fun submitRegister() {
        val s = _register.value
        if (!s.canSubmit || s.isSubmitting) return

        viewModelScope.launch {
            _register.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            val result = repository.register(
                RegisterRequestDto(
                    username = s.name.trim(),
                    email = s.email.trim(),
                    phone = s.phone.trim(),
                    password = s.pass
                )
            )
            result.fold(
                onSuccess = { _register.update { it.copy(isSubmitting = false, success = true, errorMsg = null) } },
                onFailure = { e -> _register.update { it.copy(isSubmitting = false, success = false, errorMsg = e.message) } }
            )
        }
    }

    fun clearRegisterResult() {
        _register.update { it.copy(success = false, errorMsg = null) }
    }

    // --- LOGOUT ---
    fun logout() {
        repository.logout()
        _currentUserId.value = null
    }
}
