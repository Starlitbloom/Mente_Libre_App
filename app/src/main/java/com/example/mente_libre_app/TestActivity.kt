package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mente_libre_app.ui.screen.AjustesScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme

class TestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mente_Libre_AppTheme(selectedTheme = "Pink") {

                // Puedes simular los callbacks vacíos por ahora
                AjustesScreen(
                    onItemSelected = {},
                    onPerfilClick = { /* Mostrar toast o log */ },
                    onSeguridadClick = {},
                    onNotificacionesClick = {},
                    onDispositivosClick = {},
                    onEmergenciaClick = {},
                    onSoporteClick = {},
                    onSugerenciasClick = {},
                    onCerrarSesion = {}
                )
            }
        }
    }
}