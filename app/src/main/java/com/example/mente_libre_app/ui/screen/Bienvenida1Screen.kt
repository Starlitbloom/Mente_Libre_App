package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.PaginatorDots
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.MainColor

@Composable
fun Bienvenida1Screen(onNext: () -> Unit) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDC4C5)),
        contentAlignment = Alignment.Center
    ) {

        // 🔹 Indicadores de Paginación
        PaginatorDots(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            count = 4,
            selectedIndex = 0
        )

        // 🔹 Fondo blanco curvo
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.42f)
                .align(Alignment.BottomCenter),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 120.dp, topEnd = 120.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Personaliza tu camino\nhacia el bienestar",
                        color = MainColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = serifBold,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // 🔹 Botón con cambio de color al presionar
                    FloatingActionButton(
                        onClick = { onNext() },
                        containerColor = if (isPressed) MainColor else ButtonMagenta,
                        shape = CircleShape,
                        modifier = Modifier
                            .size(88.dp)
                            .offset(y = (-8).dp),
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        ),
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = "Avanzar",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }

        // 🔹 Imagen central
        Image(
            painter = painterResource(id = R.drawable.mascota_bienvenida1),
            contentDescription = "Hámster central",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .size(3600.dp)
                .offset(y = (-80).dp)
        )
    }
}
