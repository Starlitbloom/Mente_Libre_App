package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import kotlinx.coroutines.delay

@Composable
fun MascotaScreen(onNext: () -> Unit) {

    LaunchedEffect(Unit) {
        delay(3000)
        onNext()
    }

    // Paleta dinámica
    val colorScheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(extra.inBackground)   // FONDO DINÁMICO
            .padding(horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "¡Hola! ¡Bienvenido a\nMente Libre!\n \n" +
                    "Soy tu futura\ncompañera de bienestar\ny estoy aquí para\nayudarte en cada paso.\n \n" +
                    "Pero antes de continuar,\n¿qué te parecería elegir\n" +
                    "un compañero fiel que\nte acompañe en este\ncamino?",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.source_serif_pro_bold)),
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            color = Color.White         // COLOR DE TEXTO DINÁMICO
        )
    }
}
