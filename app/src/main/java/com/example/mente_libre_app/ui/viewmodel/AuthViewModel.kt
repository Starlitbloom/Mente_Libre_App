package com.example.mente_libre_app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.auth.RegisterRequestDto
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
    private val repository: UserRepository
) : ViewModel() {

    private val _login = MutableStateFlow(LoginUiState())
    val login = _login.asStateFlow()

    private val _register = MutableStateFlow(RegisterUiState())
    val register = _register.asStateFlow()

    private val _currentUserId = MutableStateFlow<Long?>(null)
    val currentUserId = _currentUserId.asStateFlow()

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
        val s = _login.value
        if (!s.canSubmit || s.isSubmitting) return

        viewModelScope.launch {
            _login.update { it.copy(isSubmitting = true, errorMsg = null) }

            val result = repository.login(s.email, s.pass)
            result.fold(
                onSuccess = {
                    _currentUserId.value = it.userId
                    _login.update { l -> l.copy(isSubmitting = false, success = true) }
                },
                onFailure = { e ->
                    _login.update { l -> l.copy(isSubmitting = false, errorMsg = e.message) }
                }
            )
        }
    }

    // ---- REGISTER INPUTS ----
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
                onSuccess = {
                    _register.update { it.copy(isSubmitting = false, success = true) }
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

}





