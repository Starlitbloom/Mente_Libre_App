package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BottomNavigationBar
import com.example.mente_libre_app.ui.theme.LocalExtraColors

@Composable
fun DiarioScreen(
    navController: NavHostController,
    onOpenDiarioGratitud: () -> Unit,
    onOpenLineasDeAyuda: () -> Unit,
    onOpenDesafiosSemanales: () -> Unit,
    onNavChange: (Int) -> Unit
) {
    val scroll = rememberScrollState()
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    val weekCheck = remember { mutableStateListOf(false, false, false, false, false, false, false) }
    val days = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    val extra = LocalExtraColors.current
    val scheme = MaterialTheme.colorScheme

    Scaffold(
        containerColor = Color(0xFFFFEDF5),
        bottomBar = {
            BottomNavigationBar(
                selectedItem = "diario",
                onItemSelected = { route ->
                    when (route) {
                        "inicio" -> onNavChange(0)
                        "companera" -> onNavChange(1)
                        "diario" -> onNavChange(2)
                        "ajustes" -> onNavChange(3)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scroll)
        ) {

            Spacer(Modifier.height(25.dp))

            // TITULO EXACTO
            Text(
                "Bitácora",
                fontSize = 30.sp,
                fontFamily = serifBold,
                color = extra.title
            )

            Spacer(Modifier.height(14.dp))

            // TARJETA DE CÍRCULOS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = scheme.background)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        days.forEachIndexed { i, label ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    weekCheck[i] = !weekCheck[i]
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (weekCheck[i]) Color(0xFFBF8FAE)
                                            else Color(0xFFDDBFCC)
                                        )
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    label,
                                    fontSize = 13.sp,
                                    fontFamily = serifBold,
                                    color = extra.title
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ASISTENCIA
            Text(
                "Asistencia",
                fontSize = 30.sp,
                fontFamily = serifBold,
                color = extra.title
            )

            Spacer(Modifier.height(18.dp))

            BitacoraItemStyled("Líneas de ayuda") { onOpenLineasDeAyuda() }
            Spacer(Modifier.height(18.dp))

            BitacoraItemStyled("Diario de Gratitud") { onOpenDiarioGratitud() }
            Spacer(Modifier.height(18.dp))

            BitacoraItemStyled("Desafíos Semanales") { onOpenDesafiosSemanales() }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun BitacoraItemStyled(text: String, onClick: () -> Unit) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val extra = LocalExtraColors.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp) // ← AÚN MÁS GRANDE (como en tu mockup)
            .shadow(
                elevation = 12.dp,   // sombra más amplia y suave
                shape = RoundedCornerShape(32.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(32.dp)) // más redondeadas
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 42.dp),   // Más aire a los lados
        contentAlignment = Alignment.Center    // ← CENTRADO PERFECTO
    ) {
        Text(
            text = text,
            fontFamily = serifBold,
            fontWeight = FontWeight.Bold,
            fontSize = 27.sp,  // ← UN POCO MÁS GRANDE
            color = extra.title,
            textAlign = TextAlign.Center
        )
    }
}
