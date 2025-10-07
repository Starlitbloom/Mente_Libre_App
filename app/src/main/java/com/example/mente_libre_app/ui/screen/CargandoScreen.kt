package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.AppCircle
import com.example.mente_libre_app.ui.components.GradientLoader
import com.example.mente_libre_app.ui.components.StatusBarColor
import kotlinx.coroutines.delay


@Composable
fun CargandoScreen(onNext: () -> Unit) {
    // Barra de estado
    StatusBarColor(
        color = Color(0xFFDC4F79)
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD94775),
            Color(0xFFFCB5A7)
        )
    )

    LaunchedEffect(Unit) {
        delay(3000)
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp) // Espacio entre loader y texto
            ) {
                GradientLoader()
                Text(
                    text = "Cargando...",
                    fontSize = 29.sp,
                    fontFamily = FontFamily(
                        Font(R.font.source_serif_pro_semibold)
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
