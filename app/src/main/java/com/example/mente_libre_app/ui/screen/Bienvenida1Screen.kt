package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

val ButtonMagenta = Color(0xFFE9547C)
val MainColor = Color(0xFF842C46)

@Composable
fun Bienvenida1Screen(onNext: () -> Unit) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))

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
            // 🔹 Box para centrar texto y botón
            Box(
                modifier = Modifier.fillMaxSize(),
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

                    Spacer(modifier = Modifier.height(40.dp))

                    FloatingActionButton(
                        onClick = onNext,
                        containerColor = ButtonMagenta,
                        shape = CircleShape,
                        modifier = Modifier.size(70.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Avanzar",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
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
