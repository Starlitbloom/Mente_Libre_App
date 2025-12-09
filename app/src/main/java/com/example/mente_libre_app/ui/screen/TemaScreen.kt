package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.theme.ExtraGreen
import com.example.mente_libre_app.ui.theme.ExtraPink
import com.example.mente_libre_app.ui.theme.ExtraPurple
import com.example.mente_libre_app.ui.theme.LocalExtraColors
import com.example.mente_libre_app.ui.theme.getColorSchemeForTheme
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

@Composable
fun TemaScreen(
    usuarioViewModel: UsuarioViewModel,
    onNext: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // Lista de temas
    val temas = listOf("Rosado", "Morado", "Verde")
    var indexTema by remember { mutableStateOf(0) }

    // Tema en previsualización
    val temaActual = temas[indexTema]

    // Paleta Material 3
    val colorScheme = getColorSchemeForTheme(temaActual)

    // Paleta extendida
    val extraColors = when (temaActual) {
        "Morado" -> ExtraPurple
        "Verde" -> ExtraGreen
        else -> ExtraPink
    }

    // Envuelve TODO en MaterialTheme + CompositionLocalProvider
    CompositionLocalProvider(LocalExtraColors provides extraColors) {

        MaterialTheme(colorScheme = colorScheme) {

            val extra = LocalExtraColors.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                // TÍTULO
                Text(
                    text = "Temas",
                    color = extra.title,
                    fontFamily = serifBold,
                    fontSize = 34.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // CÍRCULO CON DEGRADÉ Y BORDE EXACTO
                val gradiente = Brush.verticalGradient(
                    listOf(extra.gradientTop, extra.gradientBottom)
                )

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(gradiente)
                        .border(3.dp, extra.circleBorder, CircleShape)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Elige el estilo que refleje tu energía.\nCada tema cambia los colores.",
                    color = colorScheme.onBackground,
                    fontFamily = serifRegular,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Flechas + nombre de tema
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // IZQUIERDA
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape)
                            .background(extra.arrowBackground)   // Ahora sí el fondo correcto
                            .border(3.dp, extra.arrowBorder, CircleShape)
                            .clickable {
                                indexTema = if (indexTema > 0) indexTema - 1 else temas.size - 1
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("<", color = extra.arrowColor, fontSize = 30.sp)
                    }


                    // NOMBRE
                    Text(
                        text = temaActual,
                        color = extra.title,
                        fontFamily = serifBold,
                        fontSize = 24.sp
                    )

                    // DERECHA
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape)
                            .background(extra.arrowBackground)
                            .border(3.dp, extra.arrowBorder, CircleShape)
                            .clickable {
                                indexTema = if (indexTema > 0) indexTema - 1 else temas.size - 1
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(">", color = extra.arrowColor, fontSize = 30.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // BOTÓN SIGUIENTE
                Button(
                    onClick = {
                        usuarioViewModel.setTema(temaActual)
                        onNext()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = extra.buttonAlt,
                        contentColor = colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(65.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Siguiente", fontFamily = serifRegular, fontSize = 30.sp)
                }

                Spacer(modifier = Modifier.height(25.dp))

                // PROGRESO
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(colorScheme.surface)
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text(
                        "4 de 5",
                        color = colorScheme.onSurface,
                        fontFamily = serifRegular,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
