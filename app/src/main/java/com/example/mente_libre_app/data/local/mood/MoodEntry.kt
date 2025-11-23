package com.example.mente_libre_app.data.local.mood

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Registro de estado de ánimo por fecha (una fila por día).
 * Guardamos la fecha en formato ISO (yyyy-MM-dd) como String para evitar TypeConverters.
 *
 * AHORA también puede guardar texto de "diario de gratitud" asociado a ese día.
 */
@Entity(
    tableName = "mood_entries",
    indices = [Index(value = ["date"], unique = true)] // 1 registro por día
)
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "date")
    val date: String, // ISO yyyy-MM-dd

    @ColumnInfo(name = "mood_type")
    val moodType: String, // Debe coincidir con los labels del enum Mood (ej: "Feliz", "Triste", etc.)

    // NUEVOS CAMPOS PARA DIARIO DE GRATITUD
    @ColumnInfo(name = "gratitude_title")
    val gratitudeTitle: String? = null,

    @ColumnInfo(name = "gratitude_text")
    val gratitudeText: String? = null
)
