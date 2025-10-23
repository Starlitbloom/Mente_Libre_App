package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.ui.viewmodel.MascotaViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectorScreen(
    viewModel: MascotaViewModel = viewModel(),
    onMascotaElegida: (String) -> Unit // aquí la lambda se define afuera
) {
    val context = LocalContext.current
    val mascotaElegida by viewModel.mascotaElegida.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 7 }) // cantidad de mascotas
    val mascotas = listOf("Hamster", "Mapache", "Zorro", "Perro", "Nutria", "Oveja", "Gato")

    LaunchedEffect(Unit) {
        viewModel.cargarMascota(context)
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (mascotas[page]) {
            "Hamster" -> HamsterScreen(
                esSeleccionada = mascotaElegida == "Hamster",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Hamster")
                    onMascotaElegida("Hamster") // la navegación se hace afuera
                }
            )
            "Mapache" -> MapacheScreen(
                esSeleccionada = mascotaElegida == "Mapache",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Mapache")
                    onMascotaElegida("Mapache") // la navegación se hace afuera
                }
            )
            "Zorro" -> ZorroScreen(
                esSeleccionada = mascotaElegida == "Zorro",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Zorro")
                    onMascotaElegida("Zorro") // la navegación se hace afuera
                }
            )
            "Perro" -> PerroScreen(
                esSeleccionada = mascotaElegida == "Perro",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Perro")
                    onMascotaElegida("Perro") // la navegación se hace afuera
                }
            )
            "Nutria" -> NutriaScreen(
                esSeleccionada = mascotaElegida == "Nutria",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Nutria")
                    onMascotaElegida("Nutria") // la navegación se hace afuera
                }
            )
            "Oveja" -> OvejaScreen(
                esSeleccionada = mascotaElegida == "Oveja",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Oveja")
                    onMascotaElegida("Oveja") // la navegación se hace afuera
                }
            )
            "Gato" -> GatoScreen(
                esSeleccionada = mascotaElegida == "Gato",
                onElegirClick = {
                    viewModel.guardarMascota(context, "Gato")
                    onMascotaElegida("Gato") // la navegación se hace afuera
                }
            )
        }
    }
}
