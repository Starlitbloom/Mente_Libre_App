package com.example.mente_libre_app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory

// Pantallas a probar
import com.example.mente_libre_app.ui.screen.TemaScreen
import com.example.mente_libre_app.ui.screen.HuellaScreen

class TestActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val usuarioViewModel: UsuarioViewModel =
                viewModel(factory = UsuarioViewModelFactory(this))

            val temaSeleccionado = usuarioViewModel.tema.collectAsState().value

            Mente_Libre_AppTheme(selectedTheme = temaSeleccionado) {

                NavHost(
                    navController = navController,
                    startDestination = "tema"
                ) {

                    composable("tema") {
                        TemaScreen(
                            usuarioViewModel = usuarioViewModel,
                            onNext = { navController.navigate("huella") }
                        )
                    }

                    composable("huella") {
                        HuellaScreen(
                            activity = this@TestActivity,
                            onVerificado = { }
                        )
                    }
                }
            }
        }
    }
}
