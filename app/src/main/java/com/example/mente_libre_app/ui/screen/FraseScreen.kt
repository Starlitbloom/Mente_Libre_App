package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import kotlinx.coroutines.delay

@Composable
fun FraseScreen(onNext: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(3000)
        onNext()
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val fontSize = (screenWidth * 0.110).sp // 8% del ancho de pantalla

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4865A))
            .padding(horizontal = 30.dp), // espacio a los lados
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\"Hoy comienza tu viaje hacia una mente más libre.\"",
            fontSize = fontSize,
            fontFamily = FontFamily(
                Font(R.font.source_serif_pro_bold)
            ),
            textAlign = TextAlign.Center,
            lineHeight = 60.sp, // Más espacio entre líneas si se rompe
            color = Color.White
        )
    }
}
