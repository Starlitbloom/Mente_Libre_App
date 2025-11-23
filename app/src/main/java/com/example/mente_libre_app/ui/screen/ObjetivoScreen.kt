package com.example.mente_libre_app.ui.screen
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.Gray
import com.example.mente_libre_app.ui.theme.MainColor
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModelFactory

@Composable
fun ObjetivoScreen(onNext: () -> Unit) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val usuarioViewModel: UsuarioViewModel = viewModel(
        factory = UsuarioViewModelFactory(LocalContext.current)
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

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
            Spacer(modifier = Modifier.height(40.dp))
            // 🔹 Título
            Text(
                text = "¿Cuál es tu objetivo de salud para hoy?",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 29.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Opciones
            opciones.forEach { opcion ->
                ObjetivoOption(
                    texto = opcion,
                    seleccionado = opcionSeleccionada == opcion,
                    onClick = { opcionSeleccionada = opcion }
                )
                Spacer(modifier = Modifier.height(19.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Colores base y presionado
            val baseColor = if (opcionSeleccionada != null) Color(0xFFD94775) else Color(0xFF8688A8)
            val pressedColor = Color(0xFF842C46)

            val animatedColor by animateColorAsState(
                targetValue = if (isPressed) pressedColor else baseColor,
                label = "buttonPressAnimation"
            )

            Button(
                onClick = {
                    opcionSeleccionada?.let { objetivo ->
                        usuarioViewModel.setObjetivo(objetivo) // guardamos en memoria
                        onNext() // seguimos a la siguiente pantalla
                    }
                },
                enabled = opcionSeleccionada != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = animatedColor,
                    disabledContainerColor = Color(0xFF8688A8) // cuando está desactivado
                ),
                shape = RoundedCornerShape(50.dp),
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp)
            ) {
                Text(
                    text = "Siguiente",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontFamily = serifRegular,
                    fontSize = 30.sp
                )
            }

            }

            Spacer(modifier = Modifier.height(26.dp))

            // 🔹 Indicador de progreso
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFFFFFFF))
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "1 de 5",
                    color = Color(0xFFC5A3B3),
                    fontFamily = serifRegular,
                    fontSize = 16.sp
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
    val borderColor = if (seleccionado) Color(0xFFF95C1E) else Color(0xFF8688A8)
    val borderWidth = if (seleccionado) 2.dp else 1.dp

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD3B1)),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        border = BorderStroke(borderWidth, borderColor),
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
                color = Color(0xFF842C46),
                fontFamily = serifRegular,
                fontSize = 17.sp
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(2.dp, borderColor, CircleShape)
                    .background(if (seleccionado) Color(0xFFF95C1E) else Color.Transparent, CircleShape)
            )
        }
    }
}
