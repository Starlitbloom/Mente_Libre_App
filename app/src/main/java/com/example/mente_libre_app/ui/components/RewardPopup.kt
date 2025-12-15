package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R

@Composable
fun RewardPopup(
    puntosGanados: Int,
    nuevoTotal: Int,
    onDismiss: () -> Unit
) {
    val serifBold = FontFamily(Font(com.example.mente_libre_app.R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFFFFE9EB),
            tonalElevation = 8.dp,
            modifier = Modifier
                .padding(26.dp)
                .clickable(enabled = false) { }
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Recibiendo Puntos!",
                    fontFamily = serifBold,
                    fontSize = 20.sp,
                    color = Color(0xFFAE445A),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    "Registro completado +$puntosGanados Puntos de ML agregados!",
                    fontFamily = serifRegular,
                    fontSize = 16.sp,
                    color = Color(0xFFAE445A),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Tu puntuación de ML ha aumentado a $nuevoTotal!",
                    fontFamily = serifRegular,
                    fontSize = 16.sp,
                    color = Color(0xFFAE445A),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDA6C83),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Genial, Gracias!")
                }
            }
        }
    }
}
