package com.example.mente_libre_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun Mente_Libre_AppTheme(
    selectedTheme: String?,
    content: @Composable () -> Unit
) {
    val colorScheme = getColorSchemeForTheme(selectedTheme ?: "Rosado")

    val extra = when (selectedTheme) {
        "Morado" -> ExtraPurple
        "Verde" -> ExtraGreen
        else -> ExtraPink
    }

    CompositionLocalProvider(LocalExtraColors provides extra) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
