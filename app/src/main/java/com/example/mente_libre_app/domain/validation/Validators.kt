package com.example.mente_libre_app.domain.validation

import android.util.Patterns

// ----------------- EMAIL -----------------
fun validateEmail(email: String): String? {
    if (email.isBlank()) return "Email obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if (!ok) "Email inválido" else null
}

// ----------------- NOMBRE -----------------
fun validateNameLettersOnly(name: String): String? {
    if (name.isBlank()) return "Apodo obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if (!regex.matches(name)) "Solo letras y espacios" else null
}

// ----------------- TELÉFONO -----------------
fun validatePhoneDigitsOnly(phone: String): String? {
    if (phone.isBlank()) return "Teléfono obligatorio"
    if (!phone.all { it.isDigit() }) return "Solo números"
    if (phone.length !in 8..15) return "Debe tener entre 8 y 15 dígitos"
    return null
}

// ----------------- CONTRASEÑA FUERTE -----------------
fun validateStrongPassword(pass: String): String? {
    if (pass.isBlank()) return "Contraseña obligatoria"
    if (pass.length < 8) return "Mínimo 8 caracteres"
    if (!pass.any { it.isUpperCase() }) return "Debe incluir una mayúscula"
    if (!pass.any { it.isLowerCase() }) return "Debe incluir una minúscula"
    if (!pass.any { it.isDigit() }) return "Debe incluir un número"
    if (!pass.any { !it.isLetterOrDigit() }) return "Debe incluir un símbolo"
    if (pass.contains(' ')) return "No debe contener espacios"
    return null
}

// ----------------- CONFIRMACIÓN -----------------
fun validateConfirm(pass: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirma tu contraseña"
    return if (pass != confirm) "Las contraseñas no coinciden" else null
}