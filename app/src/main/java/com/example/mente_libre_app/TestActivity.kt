package com.example.mente_libre_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.data.local.database.AppDatabase
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.ui.screen.CrearScreen
import com.example.mente_libre_app.ui.screen.CrearScreenVm
import com.example.mente_libre_app.ui.screen.IniciarScreenVm
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

                IniciarScreenVm(
                    authViewModel = authViewModel,  //  ahora se pasa
                    onRegisterClick = { println("Botón comenzar presionado") },
                    onLoginSuccess = { println("Botón login presionado") }
                )
            }
        }
    }
}


