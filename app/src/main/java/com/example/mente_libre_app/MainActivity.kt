package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.example.mente_libre_app.navigation.AppNavGraph
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            // btener ViewModel
            val usuarioViewModel: UsuarioViewModel =
                viewModel(factory = UsuarioViewModelFactory(this))

            // Observar el tema actual
            val temaSeleccionado by usuarioViewModel.tema.collectAsState()

            // Pasarlo al Theme Global
            Mente_Libre_AppTheme(selectedTheme = temaSeleccionado) {

                val navController = rememberAnimatedNavController()

                // Navegación de la app
                AppNavGraph(
                    navController = navController,
                    activity = this
                )
            }
        }
    }
}
