package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.data.local.database.AppDatabase
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.ui.screen.GatoScreen
import com.example.mente_libre_app.ui.screen.HamsterScreen
import com.example.mente_libre_app.ui.screen.MapacheScreen
import com.example.mente_libre_app.ui.theme.Mente_Libre_AppTheme
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import com.example.mente_libre_app.ui.viewmodel.AuthViewModelFactory

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la DB y DAO
        val dao = AppDatabase.getInstance(applicationContext).userDao()
        val repository = UserRepository(dao)
        val factory = AuthViewModelFactory(repository)

        setContent {
            Mente_Libre_AppTheme {
                val authViewModel: AuthViewModel = viewModel(factory = factory)

                GatoScreen(
                    onElegirClick = {
                        println("Elegiste al hámster 🐹")
                    }
                )
            }
        }
    }
}
