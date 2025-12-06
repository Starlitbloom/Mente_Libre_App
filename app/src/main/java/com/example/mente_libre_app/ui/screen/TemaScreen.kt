package com.example.mente_libre_app.ui.screen

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

@Composable
fun TemaScreen(
    usuarioViewModel: UsuarioViewModel,
    onNext: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    // lista de temas
    val temas = listOf("Rosado", "Morado", "Verde")

    var indexTema by remember { mutableStateOf(0) }

    // gradientes según el tema
    val gradiente = when (temas[indexTema]) {
        "Rosado" -> Brush.verticalGradient(listOf(Color(0xFFE45088), Color(0xFFF9B4C6)))
        "Morado" -> Brush.verticalGradient(listOf(Color(0xFF9D4EDD), Color(0xFFD4B3FF)))
        "Verde" -> Brush.verticalGradient(
            listOf(Color(0xFF4CAF50), Color(0xFFC8E6C9))
        )
        else -> Brush.verticalGradient(listOf(Color(0xFFE45088), Color(0xFFF9B4C6)))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary) // FONDO DINÁMICO
            .padding(horizontal = 24.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Temas",
            color = MaterialTheme.colorScheme.onBackground,  // TEXTO DINÁMICO
            fontFamily = serifBold,
            fontSize = 34.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // CÍRCULO GRANDE CON BORDE DINÁMICO
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(gradiente)
                .border(3.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Elige el estilo que refleje tu energía.\nCada tema cambia los colores y la sensación de tu aplicación.",
            color = MaterialTheme.colorScheme.onBackground, // TEXTO DINÁMICO
            fontFamily = serifRegular,
            fontSize = 17.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Botones izquierda y derecha
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)    //
                    .border(3.dp, MaterialTheme.colorScheme.tertiary, CircleShape) //
                    .clickable {
                        if (indexTema > 0) indexTema-- else indexTema = temas.size - 1
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "<",
                    color = MaterialTheme.colorScheme.tertiary, //
                    fontFamily = serifBold,
                    fontSize = 30.sp
                )
            }

            Text(
                text = temas[indexTema],
                color = MaterialTheme.colorScheme.onBackground,  //
                fontFamily = serifBold,
                fontSize = 24.sp
            )

            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)    //
                    .border(3.dp, MaterialTheme.colorScheme.tertiary, CircleShape) //
                    .clickable {
                        if (indexTema < temas.size - 1) indexTema++ else indexTema = 0
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ">",
                    color = MaterialTheme.colorScheme.tertiary, //
                    fontFamily = serifBold,
                    fontSize = 30.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                usuarioViewModel.setTema(temas[indexTema])
                onNext()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, //
                contentColor = MaterialTheme.colorScheme.onPrimary  //
            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(65.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Siguiente",
                fontFamily = serifRegular,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Indicador de progreso
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.secondary) // 🔥
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            Text(
                text = "4 de 5",
                color = MaterialTheme.colorScheme.onBackground,  // 🔥
                fontFamily = serifRegular,
                fontSize = 15.sp
            )
        }
    }
}