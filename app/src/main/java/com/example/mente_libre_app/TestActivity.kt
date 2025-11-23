package com.example.mente_libre_app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.ui.screen.FotoScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

class TestActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Mente_Libre_AppTheme(selectedTheme = null) {
                val usuarioViewModel: UsuarioViewModel = viewModel()
                FotoScreen(
                    usuarioViewModel = usuarioViewModel,
                    onNext = {}
                )
            }
        }
    }
}
