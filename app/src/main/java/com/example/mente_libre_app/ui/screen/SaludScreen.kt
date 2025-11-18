package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mente_libre_app.R

@Composable
fun SaludScreen(navController: NavController) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF9AB7), Color(0xFFFFEAF4))
                )
            )
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        // 🔙 Botón atrás
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Título principal
        Text(
            text = "Salud Física",
            fontFamily = serifBold,
            fontSize = 26.sp,
            color = Color(0xFF842C46),
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 1
        Text(
            text = "Ejercicios de Oficina",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Actividades de Ocio",
            text = "Encuentra tiempo para actividades que disfrutes y que te relajen, como jardinería, cocinar, o cualquier otra afición. Esto es crucial para tu bienestar mental.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Momentos de Desconexión",
            text = "Reserva momentos específicos en el día para desconectar completamente de las responsabilidades laborales, ya sea leyendo un libro, viendo una serie o simplemente descansando.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Micro Descansos",
            text = "Tómate pequeños descansos durante el día para desconectarte y recargar energías. Un paseo corto o una pausa para un té pueden hacer maravillas.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 2
        Text(
            text = "Ejercicio Regular",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Pequeñas Sesiones de Ejercicio",
            text = "Incorpora ejercicios de corta duración durante el día, como una caminata rápida durante el almuerzo o una sesión de estiramiento en la mañana.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Clases de Ejercicio en Grupo",
            text = "Participa en clases de yoga, pilates, o cualquier actividad física en grupo que también te permita socializar.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 3
        Text(
            text = "Alimentación Saludable",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Planifica Tus Comidas",
            text = "Prepara comidas saludables y equilibradas para llevar al trabajo. Comer bien te proporcionará la energía necesaria para afrontar el día.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Hidratación",
            text = "Mantén una botella de agua contigo y asegúrate de mantenerte hidratado durante todo el día.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun TextSection4(title: String, text: String, fontRegular: FontFamily) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = "• $title:",
            color = Color(0xFF842C46),
            fontWeight = FontWeight.Bold,
            fontFamily = fontRegular,
            fontSize = 16.sp
        )
        Text(
            text = text,
            color = Color(0xFF842C46),
            fontFamily = fontRegular,
            fontSize = 15.sp,
            lineHeight = 20.sp
        )
    }
}