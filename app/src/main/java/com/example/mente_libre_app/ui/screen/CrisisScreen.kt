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
import com.example.mente_libre_app.ui.components.CurvedHeader
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun CrisisScreen(navController: NavController) {
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
                text = "¿Qué hacer en una crisis emocional?",
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
                text = "Respiración Profunda",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            // Sección 2
            Text(
                text = "Encuentra un Espacio Seguro",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            // Sección 3
            Text(
                text = "Comunica tus Necesidades",
                color = extra.arrowColor,
                fontFamily = serifBold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

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
}

