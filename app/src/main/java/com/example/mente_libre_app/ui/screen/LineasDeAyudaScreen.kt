package com.example.mente_libre_app.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mente_libre_app.R
import com.example.mente_libre_app.ui.components.BackArrowCustom

@Composable
fun LineasDeAyudaScreen(
    onBack: () -> Unit
) {
    val scroll = rememberScrollState()
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEEF4))
            .verticalScroll(scroll)
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(34.dp)
                    .clickable { onBack() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = "¿Necesitas ayuda?",
                fontSize = 30.sp,
                fontFamily = serifBold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ------------------ TEXTO PRINCIPAL ------------------
        Text(
            text = "Estamos aquí para apoyarte en cada paso de tu camino. " +
                    "Si sientes que necesitas hablar con alguien, te recomendamos primero " +
                    "contactar a un profesional o a tus redes de apoyo.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Eres fuerte y valiente. Buscar apoyo demuestra tu gran capacidad de cuidado personal.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(32.dp))


        // ------------------ LÍNEAS DE CRISIS ------------------
        Text(
            text = "Líneas de crisis",
            fontSize = 22.sp,
            fontFamily = serifBold,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE45F1C)
        )

        Spacer(Modifier.height(16.dp))

        LineaItem(
            titulo = "Línea Prevención del Suicidio",
            descripcion = "Disponible todos los días,\nlas 24 horas del día.\nProfesionales capacitados para escucharte y apoyarte.",
            phoneNumber = "6003607777"
        )

        Spacer(Modifier.height(32.dp))


        // ------------------ OTRAS LÍNEAS ------------------
        Text(
            text = "Otras líneas de ayuda",
            fontSize = 22.sp,
            fontFamily = serifBold,
            color = Color(0xFFE45F1C)
        )

        Spacer(Modifier.height(20.dp))

        LineaItem(
            titulo = "Fono Drogas y Alcohol",
            descripcion = "Disponible todo el año,\nlas 24 horas del día.",
            phoneNumber = "1412"
        )

        Spacer(Modifier.height(20.dp))

        LineaItem(
            titulo = "Fono Violencia contra la Mujer",
            descripcion = "Atención 24/7 durante todo el año.",
            phoneNumber = "1455"
        )

        Spacer(Modifier.height(20.dp))

        LineaItem(
            titulo = "Fono Mayor",
            descripcion = "Apoyo para adultos mayores.\nDisponible de lunes a viernes.",
            phoneNumber = "800400035"
        )

        // ------------------ CONSEJOS ------------------
        Spacer(Modifier.height(20.dp))

        Text(
            text = "Consejos",
            fontSize = 24.sp,
            fontFamily = serifBold,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE45F1C)
        )

        Spacer(Modifier.height(24.dp))


// --------- 1) RESPIRA PROFUNDAMENTE ----------
        Text(
            text = "Respira Profundamente",
            fontSize = 20.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Toma respiraciones lentas y profundas. Inhala contando hasta cuatro, " +
                    "sostén la respiración contando hasta cuatro, y exhala contando hasta cuatro. " +
                    "Repite varias veces.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.height(26.dp))


// --------- 2) BUSCA UN LUGAR TRANQUILO ----------
        Text(
            text = "Busca un Lugar Tranquilo",
            fontSize = 20.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Encuentra un lugar donde puedas estar solo y sentirte seguro. Puede ser una habitación, " +
                    "un rincón tranquilo o incluso un espacio al aire libre.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.height(26.dp))


// --------- 3) ESCRIBE TUS PENSAMIENTOS ----------
        Text(
            text = "Escribe tus Pensamientos",
            fontSize = 20.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Escribir puede ayudarte a procesar tus emociones. Anota lo que sientes y piensa " +
                    "en posibles soluciones o próximos pasos.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.height(26.dp))


// --------- 4) TOMA UN DESCANSO RÁPIDO ----------
        Text(
            text = "Toma un Descanso Rápido",
            fontSize = 20.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Si te sientes abrumado en el aula, considera tomar un breve descanso. Pide a un colega " +
                    "que te cubra por unos minutos mientras te tomas un respiro.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.height(26.dp))


// --------- 5) RECUERDA TU FORTALEZA ----------
        Text(
            text = "Recuerda tu Fortaleza",
            fontSize = 20.sp,
            fontFamily = serifBold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Haz una lista mental o escrita de tus logros y de las veces que has superado desafíos en el pasado. " +
                    "Esto puede darte una perspectiva más positiva y alentadora.",
            fontSize = 17.sp,
            lineHeight = 24.sp,
            fontFamily = serifRegular,
            color = Color(0xFF7A2C54),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.height(40.dp))

    }
}



@Composable
fun LineaItem(
    titulo: String,
    descripcion: String,
    phoneNumber: String
) {
    val context = LocalContext.current
    val serifBold = FontFamily(Font(R.font.source_serif_pro_bold))
    val serifRegular = FontFamily(Font(R.font.source_serif_pro_regular))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                context.startActivity(intent)
            }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        // ------- TEXTO IZQUIERDA -------
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = titulo,
                fontSize = 19.sp,
                fontFamily = serifBold,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = descripcion,
                fontSize = 16.sp,
                fontFamily = serifRegular,
                lineHeight = 22.sp,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.width(12.dp))

        // ------- ICONO DECORATIVO DERECHA -------
        Image(
            painter = painterResource(id = R.drawable.telefono_icono),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
    }
}
