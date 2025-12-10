package com.example.mente_libre_app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mente_libre_app.ui.screen.*
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory

class TestActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val usuarioViewModel: UsuarioViewModel =
                viewModel(factory = UsuarioViewModelFactory(this))

            val tema = usuarioViewModel.tema.collectAsState().value
            val foto = usuarioViewModel.fotoPerfil.collectAsState().value
            val nombreUsuario = "Usuario"

            Mente_Libre_AppTheme(selectedTheme = tema) {

                NavHost(
                    navController = navController,
                    startDestination = "inicio"
                ) {

                    // 🟢 Inicio (pantalla que contiene las Cards)
                    composable("inicio") {
                        InicioScreen(
                            navController = navController,
                            fotoPerfil = foto,
                            nombreUsuario = nombreUsuario,
                            onNavChange = {},
                            onGoAnimo = {}
                        )
                    }

                    // 🔵 Pantallas de las Cards
                    composable("organizarse") {
                        OrganizarseScreen(navController)
                    }

                    composable("crisis") {
                        CrisisScreen(navController)
                    }

                    composable("estrategias") {
                        EstrategiasScreen(navController)
                    }

                    composable("salud") {
                        SaludScreen(navController)
                    }
                }
            }
        }
    }
}
