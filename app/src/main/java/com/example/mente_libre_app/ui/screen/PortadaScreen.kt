package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.AppCircle
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import com.example.mente_libre_app.ui.components.StatusBarColor

@Composable
fun PortadaScreen(onNext: () -> Unit) {
    // Barra de estado
    StatusBarColor(
        color = Color(0xFFDC4F79),
        darkIcons = false // true si quieres íconos oscuros
    )


    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD94775),
            Color(0xFFFCB5A7)
        )
    )

    LaunchedEffect(Unit) {
        delay(2500)
        onNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .statusBarsPadding(), // <-- importante
        contentAlignment = Alignment.Center
    ) {
        AppCircle()

        Box(
            contentAlignment = Alignment.Center, // Esto centra el contenido dentro del Box
            modifier = Modifier.size(310.dp) // Define el tamaño
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_mente_libre_sombra),
                contentDescription = "Logo MenteLibre",
                modifier = Modifier.fillMaxSize() // La imagen llena el Box
            )
        }
    }
}
