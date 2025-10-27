package com.example.mente_libre_app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color


val PinkColors = lightColorScheme(
    primary = MainColor,
    secondary = ButtonMagenta,
    tertiary = BorderOrange,
    background = PinkPrimary,
    surface = White,
    onPrimary = ButtonYellow,
    onSecondary = PinkGray,
    onTertiary = IconPink,
    onBackground = MainColor,
    onSurface = Gray
)

val PurpleColors = lightColorScheme(
    primary = MainColorP,
    secondary = ButtonPurple,
    tertiary = BorderLightblue,
    background = PurplePrimary,
    surface = WhiteP,
    onPrimary = ButtonLightYellow,
    onSecondary = PurpleGray,
    onTertiary = IconPurple,
    onBackground = MainColor,
    onSurface = GrayP
)

val GreenColors = lightColorScheme(
    primary = MainColorG,
    secondary = ButtonGreen,
    tertiary = BorderOrangeG,
    background = GreenPrimary,
    surface = WhiteG,
    onPrimary = ButtonCreamYellow,
    onSecondary = PGreenGray,
    onTertiary = IconGreen,
    onBackground = MainColor,
    onSurface = GrayG
)

@Composable
fun Mente_Libre_AppTheme(
    selectedTheme: String?, // puede ser "Pink", "Purple" o "Green"
    content: @Composable () -> Unit
) {
    val colors = when (selectedTheme) {
        "Purple" -> PurpleColors
        "Green" -> GreenColors
        else -> PinkColors // por defecto rosado
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}