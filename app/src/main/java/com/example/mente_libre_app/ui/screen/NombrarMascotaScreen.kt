package com.example.mente_libre_app.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.MascotaViewModel

@Composable
fun NombrarMascotaScreen(
    mascota: String,
    onGuardarNombre: (String) -> Unit
) {
    val viewModel: MascotaViewModel = viewModel()
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }

    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifSemiBold = FontFamily(Font(R.font.source_serif_pro_semibold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val serifBlack = FontFamily(Font(R.font.source_serif_pro_black))

    val gradientColors = when (mascota) {
        "Hamster" -> listOf(Color(0xFFD94775), Color(0xFFFCB5A7))
        "Mapache" -> listOf(Color(0xFF8D57DC), Color(0xFFF2C5FE))
        "Zorro" -> listOf(Color(0xFF7DA038), Color(0xFFE0ED64))
        "Perro" -> listOf(Color(0xFFE1940D), Color(0xFFFFED99))
        "Nutria" -> listOf(Color(0xFF4469B9), Color(0xFF92DEEF))
        "Oveja" -> listOf(Color(0xFFC75118), Color(0xFFF4B778))
        "Gato" -> listOf(Color(0xFFAD2E21), Color(0xFFE3B0A8))
        else -> listOf(Color.Gray, Color.LightGray)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors))
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "¡Hola! Soy tu nuevo compañero, $mascota.",
                color = Color.White,
                fontFamily = serifBlack,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Imagen + Input + Botón
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(gradientColors[1])
                    .padding(10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val drawableRes = when (mascota) {
                        "Hamster" -> R.drawable.hamster_feliz
                        "Mapache" -> R.drawable.mapache_feliz
                        "Zorro" -> R.drawable.zorro_feliz
                        "Perro" -> R.drawable.perro_feliz
                        "Nutria" -> R.drawable.nutria_feliz
                        "Oveja" -> R.drawable.oveja_feliz
                        "Gato" -> R.drawable.gato_feliz
                        else -> R.drawable.hamster
                    }

                    Image(
                        painter = painterResource(id = drawableRes),
                        contentDescription = mascota,
                        modifier = Modifier.size(180.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    var isFocused by remember { mutableStateOf(false) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .padding(horizontal = 5.dp)
                    ) {
                        // Borde animado según foco
                        val borderColor by animateColorAsState(
                            targetValue = if (isFocused) Color(0xFFF95C1E) else Color(0xFF8688A8)
                        )
                        val borderWidth by animateDpAsState(
                            targetValue = if (isFocused) 2.dp else 1.dp
                        )

                        TextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            placeholder = { Text("Escribe un nombre", color = Color.Gray) },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = Color.Black,
                                fontFamily = serifRegular,
                                fontSize = 16.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Color(0xFFF95C1E)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .border(borderWidth, borderColor, RoundedCornerShape(20.dp))
                                .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                        )

                        Button(
                            onClick = {
                                viewModel.guardarNombre(context, nombre)
                                onGuardarNombre(nombre)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = gradientColors[0]),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .height(55.dp)
                                .padding(start = 5.dp)
                        ) {
                            Text(
                                text = "Listo",
                                color = Color.White,
                                fontFamily = serifBold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(45.dp)) // empuja los textos finales hacia abajo

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Estoy aquí para acompañarte cada día.\n¡Juntos será más fácil!",
                    color = Color.White,
                    fontFamily = serifSemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¿Cómo quieres llamarme?\nEscribe un nombre para mí.",
                    color = Color.White,
                    fontFamily = serifBold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

