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
fun CrisisScreen(navController: NavController) {
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
            text = "¿Qué hacer en una crisis emocional?",
            fontFamily = serifBold,
            fontSize = 26.sp,
            color = Color(0xFF842C46),
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 1
        Text(
            text = "Respiración Profunda",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Técnica de Respiración Box",
            text = "Inhala contando hasta cuatro, sostiene el aire por cuatro, exhala por cuatro y mantén la respiración por otros cuatro. Repite hasta sentirte más tranquilo.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Exhalaciones Prolongadas",
            text = "Enfócate en hacer tus exhalaciones más largas que las inhalaciones. Esto puede activar tu sistema nervioso parasimpático y ayudar a calmarte.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Técnica de la Respiración 4-7-8",
            text = "Inhala contando hasta cuatro, sostén la respiración contando hasta siete y exhala contando hasta ocho. Repite varias veces para calmarte rápidamente.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 2
        Text(
            text = "Encuentra un Espacio Seguro",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Sal de la Situación",
            text = "Si es posible, aléjate de la situación estresante aunque sea por unos minutos. Ve a un lugar tranquilo como la sala de profesores o al aire libre.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Técnicas de Aterrizaje",
            text = "Enfócate en lo que puedes ver, oír y sentir alrededor para reconectar con el presente y reducir la ansiedad.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Sección 3
        Text(
            text = "Comunica tus Necesidades",
            color = Color(0xFFFF6600),
            fontFamily = serifBold,
            fontSize = 20.sp
        )

        TextSection(
            title = "Haz Saber a un Colega",
            text = "Comunica a un colega de confianza que necesitas un momento para ti. A veces, simplemente saber que alguien más está al tanto puede ofrecer alivio.",
            fontRegular = serifRegular
        )

        TextSection(
            title = "Sistemas de Apoyo",
            text = "Si te sientes abrumado, no dudes en utilizar las líneas de ayuda o contactar a un profesional de salud mental.",
            fontRegular = serifRegular
        )

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun TextSection2(title: String, text: String, fontRegular: FontFamily) {
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