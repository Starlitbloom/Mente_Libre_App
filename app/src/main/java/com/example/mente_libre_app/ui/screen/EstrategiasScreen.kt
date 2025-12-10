package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.CurvedHeader
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun EstrategiasScreen(navController: NavController) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val scroll = rememberScrollState()
    val scheme = MaterialTheme.colorScheme
    val extra = LocalExtraColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(scheme.background) // fondo uniforme
            .verticalScroll(scroll)           // SOLO UN SCROLL
    ) {

        CurvedHeader(navController)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            // Título principal
            Text(
                text = "Estrategias para el autocuidado",
                fontFamily = serifBold,
                fontSize = 32.sp,
                color = extra.title,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()   // SE CENTRA
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección 1
            Text(
                text = "Tiempo para Ti",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            // Sección 2
            Text(
                text = "Ejercicio Regular",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            // Sección 3
            Text(
                text = "Alimentación Saludable",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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
}
