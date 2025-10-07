package com.example.mente_libre_app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GradientLoader(
    modifier: Modifier = Modifier.size(130.dp)
) {
    // Animación continua de rotación
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    // Gradiente: transparente → oscuro (rosa)
    val ringBrush = Brush.sweepGradient(
        colors = listOf(
            Color(0x00842C46), // transparente (inicio)
            Color(0xFF842C46)  // oscuro (final)
        )
    )

    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.22f // grosor
        val radius = (size.minDimension - strokeWidth) / 2
        val center = Offset(size.width / 2, size.height / 2)

        // 🔹 Capa base blanca (anillo completo)
        drawArc(
            color = Color.White,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // 🔹 Capa degradada que rota
        rotate(rotation, center) {
            drawArc(
                brush = ringBrush,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // 🔸 Círculo en la mitad del arco oscuro (punta del degradado)
            val angle = 360f // mitad del círculo (en grados)
            val radians = Math.toRadians(angle.toDouble()).toFloat()
            val circleX = center.x + radius * cos(radians)
            val circleY = center.y + radius * sin(radians)

            drawCircle(
                color = Color(0xFF88334C),
                radius = strokeWidth / 2,
                center = Offset(circleX, circleY)
            )
        }
    }
}