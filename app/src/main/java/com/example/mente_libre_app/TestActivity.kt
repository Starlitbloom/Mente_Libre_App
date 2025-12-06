package com.example.mente_libre_app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.ui.screen.TemaScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory

class TestActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Forzar un tema visible
            Mente_Libre_AppTheme(selectedTheme = "Rosado") {

                val usuarioViewModel: UsuarioViewModel =
                    viewModel(factory = UsuarioViewModelFactory(this))


                // Previsualizas solo esta pantalla
                TemaScreen(
                    usuarioViewModel = usuarioViewModel,
                    onNext = {}   // acción vacía
                )
            }
        }
    }
}
