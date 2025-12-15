package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.admin.DashboardSummaryDto
import com.example.mente_libre_app.data.remote.dto.admin.UserAdminDto
import com.example.mente_libre_app.data.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repo: AdminRepository
) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<UserAdminDto>>(emptyList())
    val usuarios = _usuarios

    private val _resumen = MutableStateFlow(DashboardSummaryDto())
    val resumen = _resumen.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    fun cargarDashboard() {
        viewModelScope.launch {
            try {
                _resumen.value = repo.obtenerDashboard()
            } catch (e: Exception) {
                println("ERROR DASHBOARD: ${e.message}")
                _mensaje.value = "No tienes permisos"
            }
        }
    }


    fun generarReporte() {
        viewModelScope.launch {
            repo.generarReporte()
            _mensaje.value = "Reporte generado correctamente"
        }
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val response = repo.getAllUsers()
                if (response.isSuccessful) {
                    _usuarios.value = response.body() ?: emptyList()
                } else {
                    println("Error usuarios: ${response.code()}")
                }
            } catch (e: Exception) {
                println("EXCEPCIÓN usuarios: ${e.message}")
            }
        }
    }

    fun bloquearUsuario(id: Long) {
        viewModelScope.launch {
            repo.bloquear(id)
            cargarUsuarios()
        }
    }

    fun desbloquearUsuario(id: Long) {
        viewModelScope.launch {
            repo.desbloquear(id)
            cargarUsuarios()
        }
    }
}
