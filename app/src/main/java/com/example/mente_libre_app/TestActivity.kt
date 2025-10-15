package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mente_libre_app.ui.screen.BienvenidaScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mente_Libre_AppTheme {
                BienvenidaScreen(
                    onComenzarClick = { println("Botón comenzar presionado") },
                    onLoginClick = { println("Botón login presionado") }
                )
            }
        }
    }
}
