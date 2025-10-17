package com.example.mente_libre_app.navigation

sealed class Route(val path: String) {
    data object Portada : Route("portada")
    data object Cargando : Route("cargando")
    data object Frase : Route("frase")
    data object Bienvenida1 : Route("bienvenida1")
    data object Bienvenida2 : Route("bienvenida2")
    data object Bienvenida3 : Route("bienvenida3")
    data object Bienvenida4 : Route("bienvenida4")
    data object Bienvenida : Route("bienvenida")
    data object Crear : Route("crear")
    data object FraseInicio : Route("frase_inicio")
    data object Iniciar : Route("iniciar")
}