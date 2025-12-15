package com.example.mente_libre_app.ui.components

import com.example.mente_libre_app.R

fun PetDrawable(avatarKey: String): Int {
    return when (avatarKey.lowercase()) {

        // Hamster
        "hamster" -> R.drawable.hamster
        "hamster_feliz" -> R.drawable.hamster_feliz

        // Gato
        "gato" -> R.drawable.gato
        "gato_feliz" -> R.drawable.gato_feliz

        // Mapache
        "mapache" -> R.drawable.mapache
        "mapache_feliz" -> R.drawable.mapache_feliz

        // Zorro
        "zorro" -> R.drawable.zorro
        "zorro_feliz" -> R.drawable.zorro_feliz

        // Perro
        "perro" -> R.drawable.perro
        "perro_feliz" -> R.drawable.perro_feliz

        // Nutria
        "nutria" -> R.drawable.nutria
        "nutria_feliz" -> R.drawable.nutria_feliz

        // Oveja
        "oveja" -> R.drawable.oveja
        "oveja_feliz" -> R.drawable.oveja_feliz

        // Si no coincide ninguno → imagen por defecto
        else -> R.drawable.hamster
    }
}