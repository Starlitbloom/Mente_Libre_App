package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.mente_libre_app.navigation.AppNavGraph
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Esto permite que Compose controle la barra del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Mente_Libre_AppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}


