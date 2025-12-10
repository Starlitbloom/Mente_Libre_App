package com.example.mente_libre_app.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme

fun getColorSchemeForTheme(tema: String): ColorScheme {
    return when (tema) {

        "Rosado" -> lightColorScheme(
            primary = ButtonMagenta,
            onPrimary = White,
            secondary = PinkPrimary,
            tertiary = BorderOrange,
            background = PinkPrimary,
            onBackground = MainColor
        )

        "Morado" -> lightColorScheme(
            primary = ButtonPurple,
            onPrimary = WhiteP,
            secondary = PurplePrimary,
            tertiary = BorderLightblue,
            background = PurplePrimary,
            onBackground = MainColorP,
        )

        "Verde" -> lightColorScheme(
            primary = ButtonGreen,
            onPrimary = WhiteG,
            secondary = GreenPrimary,
            tertiary = BorderOrangeG,
            background = GreenPrimary,
            onBackground = MainColorG,
        )

        else -> lightColorScheme(
            primary = ButtonMagenta,
            onPrimary = White,
            secondary = PinkPrimary,
            tertiary = BorderOrange,
            background = PinkPrimary,
            onBackground = MainColor,
        )
    }
}
