package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BackArrowCustom(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    stroke: Float = 12f,          // Grosor de la flecha
    size: Int = 42,               // Tamaño total del canvas
    navController: NavController
) {
    Canvas(
        modifier = modifier.size(size.dp)
    ) {
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(
                x = size * 0.65f,
                y = size * 0.20f
            ),
            end = androidx.compose.ui.geometry.Offset(
                x = size * 0.35f,
                y = size * 0.50f
            ),
            strokeWidth = stroke,
            cap = androidx.compose.ui.graphics.StrokeCap.Round    // 🔥 redondeado
        )

        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(
                x = size * 0.35f,
                y = size * 0.50f
            ),
            end = androidx.compose.ui.geometry.Offset(
                x = size * 0.65f,
                y = size * 0.80f
            ),
            strokeWidth = stroke,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}