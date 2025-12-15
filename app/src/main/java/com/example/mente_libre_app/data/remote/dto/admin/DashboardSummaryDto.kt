package com.example.mente_libre_app.data.remote.dto.admin

data class DashboardSummaryDto(
    val totalUsuarios: Int = 0,
    val usuariosBloqueados: Int = 0,
    val totalEvaluaciones: Int = 0,
    val totalEmociones: Int = 0,
    val totalMetas: Int = 0,
    val totalLogros: Int = 0
)