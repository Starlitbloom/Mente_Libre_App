package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.admin.DashboardSummaryDto
import com.example.mente_libre_app.data.remote.dto.admin.UserAdminDto
import com.example.mente_libre_app.data.remote.dto.admin.RolDto
import com.example.mente_libre_app.data.remote.dto.admin.ReporteDto
import retrofit2.Response
import retrofit2.http.*

interface AdminApi {

    @GET("api/admin/users")
    suspend fun getAllUsers(): Response<List<UserAdminDto>>

    @PUT("api/admin/users/bloquear/{id}")
    suspend fun bloquearUsuario(@Path("id") id: Long): Response<UserAdminDto>

    @PUT("api/admin/users/desbloquear/{id}")
    suspend fun desbloquearUsuario(@Path("id") id: Long): Response<UserAdminDto>

    @DELETE("api/admin/users/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long): Response<Unit>

    @PUT("api/admin/users/rol/{idUsuario}/{idRol}")
    suspend fun asignarRol(
        @Path("idUsuario") idUsuario: Long,
        @Path("idRol") idRol: Long
    ): Response<UserAdminDto>

    @GET("api/admin/roles")
    suspend fun getRoles(): Response<List<RolDto>>

    @POST("api/admin/roles")
    suspend fun crearRol(@Body dto: RolDto): Response<RolDto>

    @DELETE("api/admin/roles/{id}")
    suspend fun eliminarRol(@Path("id") id: Long): Response<Unit>

    @GET("api/admin/reportes")
    suspend fun getReportes(): Response<List<ReporteDto>>

    @POST("api/admin/reportes/generar")
    suspend fun generarReporte(): Response<ReporteDto>

    @GET("api/admin/dashboard")
    suspend fun getDashboard(): DashboardSummaryDto


}