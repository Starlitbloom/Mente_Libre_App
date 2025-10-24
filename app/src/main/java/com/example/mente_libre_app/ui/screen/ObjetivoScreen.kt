package com.example.mente_libre_app.ui.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import com.example.mente_libre_app.R

@Composable
fun ObjetivoScreen(onNext: () -> Unit) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // 🔸 Lista de opciones
    val opciones = listOf(
        "Quiero reducir el estrés",
        "Quiero intentar la terapia de I.A",
        "Quiero afrontar un trauma",
        "Quiero ser una mejor persona",
        "Solo estoy probando la app!"
    )

    var opcionSeleccionada by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4))
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Título
            Text(
                text = "¿Cuál es tu\nobjetivo de salud\npara hoy?",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Opciones
            opciones.forEach { opcion ->
                ObjetivoOption(
                    texto = opcion,
                    seleccionado = opcionSeleccionada == opcion,
                    onClick = { opcionSeleccionada = opcion }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(25.dp))

            // 🔹 Botón siguiente
            Button(
                onClick = { if (opcionSeleccionada != null) onNext() },
                enabled = opcionSeleccionada != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD94775),
                    disabledContainerColor = Color(0xFFEEB2C4)
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
            ) {
                Text(
                    text = "Siguiente",
                    color = Color.White,
                    fontFamily = serifBold,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 🔹 Indicador de progreso
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFE5D7D0))
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "1 de 7",
                    color = Color(0xFF842C46),
                    fontFamily = serifRegular,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// 🔸 Opción de objetivo individual
@Composable
fun ObjetivoOption(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val borderColor = if (seleccionado) Color(0xFFD94775) else Color(0xFF8688A8)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD3B1)),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                color = Color(0xFF4C3C3C),
                fontFamily = serifRegular,
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(2.dp, borderColor, CircleShape)
                    .background(if (seleccionado) Color(0xFFD94775) else Color.Transparent, CircleShape)
            )
        }
    }
}
