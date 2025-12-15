package com.example.mente_libre_app.data.remote.dto.admin

data class ReporteDto(
    val id: Long,
    val totalUsuarios: Int,
    val usuariosBloqueados: Int,
    val objetivosPorServicio: Map<String, Int>,
    val creadoEn: String
)
