package com.example.mente_libre_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PaginatorDots(
    modifier: Modifier = Modifier,
    count: Int,
    selectedIndex: Int
) {
    val mainColor = Color(0xFF842C46) // 🎨 tu color principal (vino oscuro)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp) // un poco más de separación
    ) {
        repeat(count) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .size(if (isSelected) 14.dp else 12.dp) // puntos un poco más grandes
                    .clip(CircleShape)
                    .background(if (isSelected) mainColor else Color.Transparent)
                    .border(width = 2.dp, color = mainColor, shape = CircleShape)
            )
        }
    }
}
