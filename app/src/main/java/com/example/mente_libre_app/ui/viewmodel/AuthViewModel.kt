package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.domain.validation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ----------------- ESTADO DE LA UI -----------------
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

    private val _register = MutableStateFlow(RegisterUiState())
    val register: StateFlow<RegisterUiState> = _register

    // ----------------- HANDLERS -----------------

    fun onNameChange(value: String) {
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _register.update { it.copy(name = filtered, nameError = validateNameLettersOnly(filtered)) }
        recomputeCanSubmit()
    }

    fun onEmailChange(value: String) {
        _register.update { it.copy(email = value, emailError = validateEmail(value)) }
        recomputeCanSubmit()
    }

    fun onPhoneChange(value: String) {
        val digitsOnly = value.filter { it.isDigit() }
        _register.update { it.copy(phone = digitsOnly, phoneError = validatePhoneDigitsOnly(digitsOnly)) }
        recomputeCanSubmit()
    }

    fun onPassChange(value: String) {
        _register.update {
            it.copy(
                pass = value,
                passError = validateStrongPassword(value),
                confirmError = validateConfirm(value, it.confirm)
            )
        }
        recomputeCanSubmit()
    }

    fun onConfirmChange(value: String) {
        _register.update { it.copy(confirm = value, confirmError = validateConfirm(it.pass, value)) }
        recomputeCanSubmit()
    }

    // ----------------- BOTÓN "Registrar" -----------------

    private fun recomputeCanSubmit() {
        val s = _register.value
        val noErrors = listOf(s.nameError, s.emailError, s.phoneError, s.passError, s.confirmError).all { it == null }
        val filled = s.name.isNotBlank() && s.email.isNotBlank() && s.phone.isNotBlank() && s.pass.isNotBlank() && s.confirm.isNotBlank()
        _register.update { it.copy(canSubmit = noErrors && filled) }
    }

    // ----------------- ENVÍO -----------------

    fun submitRegister() {
        val s = _register.value
        if (!s.canSubmit || s.isSubmitting) return

        viewModelScope.launch {
            _register.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }

            val result = repository.register(
                name = s.name.trim(),
                email = s.email.trim(),
                phone = s.phone.trim(),
                password = s.pass
            )

            result.fold(
                onSuccess = {
                    _register.update { it.copy(isSubmitting = false, success = true, errorMsg = null) }
                },
                onFailure = { e ->
                    _register.update { it.copy(isSubmitting = false, success = false, errorMsg = e.message) }
                }
            )
        }
    }

    fun clearRegisterResult() {
        _register.update { it.copy(success = false, errorMsg = null) }
    }
}
