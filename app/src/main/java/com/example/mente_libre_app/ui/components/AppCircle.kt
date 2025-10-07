package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.hypot
import kotlin.random.Random

@Composable
fun AppCircle(modifier: Modifier = Modifier) {
    // 🔹 Random con semilla fija → siempre genera el mismo patrón
    val random = remember { Random(42) } // cualquier número sirve como “semilla”

    val circles = remember {
        val baseSizes = listOf(10f, 15f, 20f, 30f, 40f, 50f)
        val tempList = mutableListOf<Pair<Offset, Float>>()
        val maxAttempts = 200

        repeat(20) {
            var placed = false
            var attempts = 0
            while (!placed && attempts < maxAttempts) {
                attempts++
                val radius = baseSizes.random(random)
                val x = random.nextFloat() * 1080f
                val y = random.nextFloat() * 1920f
                val center = Offset(x, y)

                val overlap = tempList.any { (otherCenter, otherRadius) ->
                    hypot(center.x - otherCenter.x, center.y - otherCenter.y) < (radius + otherRadius + 10f)
                }

                if (!overlap) {
                    tempList.add(center to radius)
                    placed = true
                }
            }
        }
        tempList
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        circles.forEach { (center, radius) ->
            drawCircle(
                color = Color.White.copy(alpha = 0.18f),
                radius = radius,
                center = center,
                style = Fill
            )
        }
    }
}