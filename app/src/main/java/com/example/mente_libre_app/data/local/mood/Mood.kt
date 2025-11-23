package com.example.mente_libre_app.data.local.mood

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mente_libre_app.R

/**
 * Enum que representa los tipos de ánimo disponibles.
 * Deben coincidir EXACTAMENTE con el campo `moodType` guardado en MoodEntry.
 */
enum class Mood(
    val label: String,
    @DrawableRes val icon: Int,
    val color: Color,
    val score: Float,
    val description: String
) {

    Feliz(
        label = "Feliz",
        icon = R.drawable.cara_feliz,
        color = Color(0xFFA6E7A6),
        score = 0.80f,
        description = "Ánimo alto, energía y motivación."
    ),

    Tranquilo(
        label = "Tranquilo",
        icon = R.drawable.cara_tranquilo,
        color = Color(0xFFFFF1A6),
        score = 0.70f,
        description = "Calma, equilibrio y serenidad."
    ),

    Sereno(
        label = "Sereno",
        icon = R.drawable.cara_sereno,
        color = Color(0xFFAEDCFF),
        score = 0.60f,
        description = "Ligero bienestar, sin estrés notable."
    ),

    Neutral(
        label = "Neutral",
        icon = R.drawable.cara_neutral,
        color = Color(0xFFD9D9D9),
        score = 0.50f,
        description = "Sin emociones fuertes, estable."
    ),

    Enojado(
        label = "Enojado",
        icon = R.drawable.cara_enojado,
        color = Color(0xFFFFB3B3),
        score = 0.40f,
        description = "Irritación, molestia o frustración."
    ),

    Triste(
        label = "Triste",
        icon = R.drawable.cara_triste,
        color = Color(0xFFCFB8FF),
        score = 0.35f,
        description = "Bajo ánimo, decaimiento o nostalgia."
    ),

    Deprimido(
        label = "Deprimido",
        icon = R.drawable.cara_deprimido,
        color = Color(0xFFB3C7FF),
        score = 0.25f,
        description = "Ánimo muy bajo; busca apoyo si persiste."
    );
}
