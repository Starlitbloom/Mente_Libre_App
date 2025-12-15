package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.remote.api.AdminApi
import com.example.mente_libre_app.data.remote.dto.admin.RolDto

class AdminRepository(
    private val api: AdminApi
) {

    suspend fun getAllUsers() = api.getAllUsers()
    suspend fun bloquear(id: Long) = api.bloquearUsuario(id)
    suspend fun desbloquear(id: Long) = api.desbloquearUsuario(id)
    suspend fun eliminar(id: Long) = api.eliminarUsuario(id)
    suspend fun asignarRol(idUser: Long, idRol: Long) = api.asignarRol(idUser, idRol)

    suspend fun getRoles() = api.getRoles()
    suspend fun crearRol(dto: RolDto) = api.crearRol(dto)
    suspend fun eliminarRol(id: Long) = api.eliminarRol(id)

    suspend fun generarReporte() = api.generarReporte()
    suspend fun getReportes() = api.getReportes()
    suspend fun obtenerDashboard() = api.getDashboard()

}