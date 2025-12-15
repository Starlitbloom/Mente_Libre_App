package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.ui.viewmodel.PetViewModel
import com.example.mente_libre_app.ui.viewmodel.PetViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectorScreen(
    petViewModel: PetViewModel = viewModel(factory = PetViewModelFactory(LocalContext.current)),
    onMascotaElegida: (String) -> Unit
) {

    val pagerState = rememberPagerState(pageCount = { 7 })
    val mascotas = listOf("Hamster", "Mapache", "Zorro", "Perro", "Nutria", "Oveja", "Gato")


    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->

        val mascota = mascotas[page]

        fun seleccionarMascota() {
            onMascotaElegida(mascota)
        }

        when (mascota) {
            "Hamster" -> HamsterScreen(onElegirClick = { seleccionarMascota() })
            "Mapache" -> MapacheScreen(onElegirClick = { seleccionarMascota() })
            "Zorro" -> ZorroScreen(onElegirClick = { seleccionarMascota() })
            "Perro" -> PerroScreen(onElegirClick = { seleccionarMascota() })
            "Nutria" -> NutriaScreen(onElegirClick = { seleccionarMascota() })
            "Oveja" -> OvejaScreen(onElegirClick = { seleccionarMascota() })
            "Gato" -> GatoScreen(onElegirClick = { seleccionarMascota() })
        }

    }
}