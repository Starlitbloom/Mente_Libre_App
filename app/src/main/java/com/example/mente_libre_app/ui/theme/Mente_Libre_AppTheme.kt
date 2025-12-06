package com.example.mente_libre_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable

@Composable
fun Mente_Libre_AppTheme(
    selectedTheme: String?,
    content: @Composable () -> Unit
) {
    // Paleta según el tema elegido
    val colorScheme = getColorSchemeForTheme(selectedTheme ?: "Rosado")

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
