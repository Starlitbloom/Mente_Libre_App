package com.example.mente_libre_app.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R

@Composable
fun MapacheScreen(
    mascotaNombre: String = "Mapache",
    mascotaDescripcion: String = "Aunque sea un poco travieso, mi lealtad contigo no tiene límites.",
    onElegirClick: () -> Unit = {},
    esSeleccionada: Boolean = false
) {
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))
    val serifBlack = FontFamily(Font(R.font.source_serif_pro_black))

    val scrollState = rememberScrollState()
    var isHappy by remember { mutableStateOf(false) } // controla si el mapache está feliz

    LaunchedEffect(esSeleccionada) {
        isHappy = esSeleccionada
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8D57DC))
            .padding(vertical = 40.dp, horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Mascota",
                color = Color.White,
                fontFamily = serifBold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = mascotaNombre,
                color = Color.White,
                fontFamily = serifBlack,
                fontSize = 40.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            // Contenedor con rectángulo y hamster sobrepuesto
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp) // suficiente para la superposición
            ) {
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(170.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .shadow(12.dp, RoundedCornerShape(30.dp))
                        .background(Color(0xFFF2C5FE))
                )

                Image(
                    painter = painterResource(
                        id = if (isHappy) R.drawable.mapache_feliz else R.drawable.mapache
                    ),
                    contentDescription = "Mascota $mascotaNombre",
                    modifier = Modifier
                        .size(if (isHappy) 400.dp else 300.dp)
                        .offset(y = 50.dp) // sobresale por abajo
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Desliza para seleccionar diferentes mascotas.",
                color = Color.White,
                fontFamily = serifRegular,
                fontSize = 19.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "\"${mascotaDescripcion}\"",
                color = Color.White,
                fontFamily = serifBold,
                fontSize = 25.sp,
                lineHeight = 35.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 1.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val glowColor = if (isPressed) Color(0xFFF379FA) else Color.Transparent

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp)
                    .background(glowColor, RoundedCornerShape(50.dp))
            ) {
                Button(
                    onClick = {
                        isHappy = !isHappy
                        onElegirClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2C5FE)),
                    shape = RoundedCornerShape(50.dp),
                    interactionSource = interactionSource,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "¡Te quiero a ti!",
                        color = Color(0xFF842C46),
                        fontFamily = serifRegular,
                        fontSize = 25.sp
                    )
                }
            }

        }
    }
}
