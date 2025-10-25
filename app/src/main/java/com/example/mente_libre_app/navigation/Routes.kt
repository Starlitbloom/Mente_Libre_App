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
    data object Objetivo : Route("objetivo")
    data object Genero : Route("genero")
    data object Foto : Route("foto")
    data object Perfil : Route("perfil")
    data object Huella : Route("huella")
    data object Mascota : Route("mascota")
    data object Selector : Route("selector")
    data object NombrarMascota : Route("nombrar_mascota/{mascota}")
    data object Iniciar : Route("iniciar")

    // 🔹 Nueva ruta para la pantalla de inicio
    data object Inicio : Route("inicio")

    // 🔹 Nueva ruta para la pantalla de Ánimo (gráfico y registro)
    data object Animo : Route("animo")
}
