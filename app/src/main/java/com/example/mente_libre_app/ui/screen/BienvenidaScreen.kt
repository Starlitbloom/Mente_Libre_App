package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.ButtonMagenta
import com.example.mente_libre_app.ui.theme.MainColor


@Composable
fun BienvenidaScreen(
    onComenzarClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4))
    ) {
        val boxHeight = maxHeight
        val boxWidth = maxWidth

        // Logo grande
        Image(
            painter = painterResource(id = R.drawable.mente_libre_app),
            contentDescription = "Logo Mente Libre",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(boxHeight * 1f) // ocupa el 70% de la altura
                .width(boxWidth * 1f)  // ocupa el 90% del ancho
                .align(Alignment.Center)
                .offset(x = 10.dp)
        )

        // 🔹 Texto superior (más arriba del logo)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp), // 👈 controla qué tan arriba va
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Te damos la bienvenida a ")
                    withStyle(SpanStyle(color = Color(0xFFD94775), fontWeight = FontWeight.Bold)) {
                        append("Mente Libre!")
                    }
                },
                fontFamily = serifBold,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                color = Color(0xFF842C46)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Porque cada mente merece ser un refugio.",
                fontSize = 16.sp,
                color = Color(0xFF842C46),
                textAlign = TextAlign.Center,
                fontFamily = serifRegular
            )
        }

        // 🔹 Botón y texto inferior
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onComenzarClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPressed) MainColor else ButtonMagenta
                ),
                shape = RoundedCornerShape(50.dp),
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(65.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Comenzar",
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontFamily = serifRegular,
                        fontSize = 34.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "Ir adelante",
                        tint = Color.White,
                        modifier = Modifier.size(29.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    color = Color(0xFF842C46),
                    fontSize = 16.sp,
                    fontFamily = serifRegular
                )
                TextButton(onClick = { onLoginClick() }) {
                    Text(
                        text = "Ingresa aquí",
                        color = Color(0xFFF95C1E),
                        fontSize = 16.sp,
                        fontFamily = serifRegular
                    )
                }
            }
        }
    }
}
