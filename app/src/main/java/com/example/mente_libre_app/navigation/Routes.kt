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
    data object Mascota : Route("mascota")
    data object Hamster : Route("hamster")
    data object Mapache : Route("mapache")
    data object Zorro : Route("zorro")
    data object Perro : Route("perro")
    data object Nutria : Route("nutria")
    data object Oveja : Route("oveja")
    data object Gato : Route("gato")
    data object Iniciar : Route("iniciar")
}