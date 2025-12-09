package com.example.mente_libre_app.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel

@Composable
fun GeneroScreen(
    usuarioViewModel: UsuarioViewModel,
    onNext: () -> Unit
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    var generoSeleccionado by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEAF4))
            .padding(horizontal = 24.dp, vertical = 30.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "¿Cuál es tu género oficial?",
                color = Color(0xFF842C46),
                fontFamily = serifBold,
                fontSize = 29.sp,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            GeneroOption(
                texto = "Soy Mujer",
                icono = R.drawable.mujer,
                color = Color(0xFFFFD3B1),
                seleccionado = generoSeleccionado == "Mujer",
                onClick = { generoSeleccionado = "Mujer" }
            )

            Spacer(modifier = Modifier.height(18.dp))

            GeneroOption(
                texto = "Soy Hombre",
                icono = R.drawable.hombre,
                color = Color(0xFFFFD3B1),
                seleccionado = generoSeleccionado == "Hombre",
                onClick = { generoSeleccionado = "Hombre" }
            )

            Spacer(modifier = Modifier.height(18.dp))

            GeneroOption(
                texto = "Prefiero no decirlo, gracias",
                color = Color(0xFFFFD3B1),
                seleccionado = generoSeleccionado == "Prefiero no decirlo",
                onClick = { generoSeleccionado = "Prefiero no decirlo" },
                modifier = Modifier.height(65.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            val animatedColor by animateColorAsState(
                targetValue =
                    if (isPressed) Color(0xFF842C46)
                    else if (generoSeleccionado != null) Color(0xFFD94775)
                    else Color(0xFF8688A8),
                label = ""
            )

            Button(
                onClick = {
                    generoSeleccionado?.let { genero ->
                        val generoId = when (genero) {
                            "Mujer" -> 1L
                            "Hombre" -> 2L
                            else -> 3L
                        }

                        usuarioViewModel.setGeneroId(generoId, genero)
                        onNext()
                    }
                },
                enabled = generoSeleccionado != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = animatedColor
                ),
                shape = RoundedCornerShape(50.dp),
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(65.dp)
            ) {
                Text("Siguiente", color = Color.White, fontFamily = serifRegular, fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.height(26.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text("2 de 5", color = Color(0xFFC5A3B3), fontFamily = serifRegular, fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun GeneroOption(
    texto: String,
    icono: Int? = null,
    color: Color,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val borderColor = if (seleccionado) Color(0xFFF95C1E) else Color(0xFF8688A8)
    val borderWidth = if (seleccionado) 2.dp else 1.dp

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(25.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        border = BorderStroke(borderWidth, borderColor),
        modifier = modifier // <-- usamos el modifier recibido
            .height(140.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = texto,
                color = Color(0xFF000000),
                fontFamily = serifRegular,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            if (icono != null) {
                Image(
                    painter = painterResource(id = icono),
                    contentDescription = texto,
                    modifier = Modifier.size(160.dp)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = texto,
                        color = Color(0xFF4C3C3C),
                        fontFamily = serifRegular,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}