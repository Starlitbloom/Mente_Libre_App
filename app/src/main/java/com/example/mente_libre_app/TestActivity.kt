package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.ui.screen.FotoScreen
import com.example.mente_libre_app.ui.screen.GeneroScreen
import com.example.mente_libre_app.ui.screen.HuellaScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mente_Libre_AppTheme {
                val usuarioViewModel: UsuarioViewModel = viewModel()
                FotoScreen(usuarioViewModel = usuarioViewModel, onNext = {})

            }
        }
    }
}