package com.example.mente_libre_app.data.remote.dto.storage

data class FileResponseDto(
    val id: Long,
    val fileName: String,
    val url: String,
    val category: String
)