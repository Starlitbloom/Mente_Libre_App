package com.example.mente_libre_app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtraColors(
    val title: Color,
    val circleBorder: Color,
    val gradientTop: Color,
    val gradientBottom: Color,
    val arrowBorder: Color,
    val arrowColor: Color,
    val buttonAlt: Color,
    val arrowBackground: Color
)

val LocalExtraColors = staticCompositionLocalOf<ExtraColors> {
    error("No extra colors provided")
}

val ExtraPink = ExtraColors(
    title = Color(0xFF842C46),
    circleBorder = Color(0xFF842C46),
    gradientTop = Color(0xFFD94775),
    gradientBottom = Color(0xFFFCB5A7),
    arrowBorder = Color(0xFFF95C1E),
    arrowColor = Color(0xFFF95C1E),
    buttonAlt = Color(0xFFD94775),
    arrowBackground = Color(0xFFFFD3B1)
)

val ExtraPurple = ExtraColors(
    title = Color(0xFF332062),
    circleBorder = Color(0xFF332062),
    gradientTop = Color(0xFF8D57DC),
    gradientBottom = Color(0xFF92F0FD),
    arrowBorder = Color(0xFF0199E5),
    arrowColor = Color(0xFF0199E5),
    buttonAlt = Color(0xFF774BE4),
    arrowBackground = Color(0xFFFFE6B1)
)

val ExtraGreen = ExtraColors(
    title = Color(0xFF0D4A25),
    circleBorder = Color(0xFF0D4A25),
    gradientTop = Color(0xFF008300),
    gradientBottom = Color(0xFFFFF7AE),
    arrowBorder = Color(0xFFE68907),
    arrowColor = Color(0xFFE68907),
    buttonAlt = Color(0xFF30B814),
    arrowBackground = Color(0xFFFFEBC0)
)
