package com.example.mente_libre_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/* ============================================================
   PANTALLA PRINCIPAL BITÁCORA
   ============================================================ */

@Composable
fun BitacoraScreen(
    onBack: () -> Unit,
    onOpenDiarioGratitud: () -> Unit,
    onOpenLineasDeAyuda: () -> Unit,
    onOpenDesafiosSemanales: () -> Unit
) {
    // Estado: qué días de la semana han sido marcados
    val weekCheck = remember {
        mutableStateListOf(false, false, false, false, false, false, false)
    }
    val dayLabels = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Barra superior
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF7A2C54)
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Bitácora",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(18.dp))

        // --- Bloque “Tu semana de cuidado 💗” ---
        Text(
            text = "Tu semana de cuidado 💗",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7A2C54)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Marca los días en que te diste un momento para ti.\n" +
                    "Puede ser algo pequeño: respirar profundo, caminar, escribir, meditar o simplemente descansar.",
            fontSize = 15.sp,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFCE7EF), RoundedCornerShape(26.dp))
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Fila de circulitos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dayLabels.forEachIndexed { index, label ->
                        val checked = weekCheck[index]
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable {
                                // Toggle: si está marcado se desmarca, y viceversa
                                weekCheck[index] = !checked
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = if (checked)
                                            Color(0xFFFFA5C8)
                                        else
                                            Color(0xFFF6DDE9),
                                        shape = CircleShape
                                    )
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = label,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF7A2C54)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Toca un día para marcarlo como día de autocuidado.\n" +
                            "Si te equivocaste, vuelve a tocar el círculo para desmarcarlo.",
                    fontSize = 12.sp,
                    color = Color(0xFF7A2C54).copy(alpha = 0.75f)
                )
            }
        }

        Spacer(Modifier.height(26.dp))

        // --- Sección asistencia ---
        Text(
            text = "Asistencia",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(18.dp))

        BitacoraItem("Líneas de ayuda") {
            onOpenLineasDeAyuda()
        }

        Spacer(Modifier.height(14.dp))

        BitacoraItem("Diario de Gratitud") {
            onOpenDiarioGratitud()
        }

        Spacer(Modifier.height(14.dp))

        BitacoraItem("Desafíos Semanales") {
            onOpenDesafiosSemanales()
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun BitacoraItem(
    text: String,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(Color(0xFFFCE7EF), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF7A2C54),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/* ============================================================
   PANTALLA: LÍNEAS DE AYUDA
   ============================================================ */

@Composable
fun LineasDeAyudaScreen(onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // --- Header ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Líneas de ayuda",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(20.dp))

        // --- INTRO ---
        Text(
            text = "¿Necesitas ayuda?",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Estamos aquí para apoyarte. Si necesitas hablar con alguien, puedes comunicarte con profesionales o servicios de apoyo emocional.",
            color = Color(0xFF7A2C54),
            fontSize = 16.sp
        )

        Spacer(Modifier.height(20.dp))

        // -------- Sección Líneas de crisis --------
        Text(
            text = "Líneas de crisis",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFFBD295A)
        )

        Spacer(Modifier.height(10.dp))

        AyudaItem(
            titulo = "Línea Prevención del Suicidio",
            descripcion = "Disponible 24/7. Atención inmediata con profesionales."
        )

        Spacer(Modifier.height(20.dp))

        // -------- Otras líneas de ayuda --------
        Text(
            text = "Otras líneas de ayuda",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFFBD295A)
        )

        Spacer(Modifier.height(12.dp))

        AyudaItem(
            titulo = "Fono Drogas y Alcohol",
            descripcion = "Atención todo el año, 24 horas del día."
        )

        Spacer(Modifier.height(12.dp))

        AyudaItem(
            titulo = "Fono Infancia",
            descripcion = "De lunes a viernes de 08:30 a 19:00 horas."
        )

        Spacer(Modifier.height(12.dp))

        AyudaItem(
            titulo = "Fono Violencia contra la Mujer",
            descripcion = "Disponible 24/7 durante todo el año."
        )

        Spacer(Modifier.height(12.dp))

        AyudaItem(
            titulo = "Fono Mayor",
            descripcion = "Lunes a viernes de 09:00 a 18:00 horas."
        )

        Spacer(Modifier.height(25.dp))

        // -------- CONSEJOS --------
        Text(
            text = "Consejos",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFFBD295A)
        )

        Spacer(Modifier.height(12.dp))

        ConsejoItem(
            titulo = "Respira profundamente",
            descripcion = "Inhala contando hasta cuatro, retén y exhala. Hazlo varias veces."
        )

        Spacer(Modifier.height(12.dp))

        ConsejoItem(
            titulo = "Busca un lugar tranquilo",
            descripcion = "Encuentra un espacio donde te sientas seguro y puedas calmarte."
        )

        Spacer(Modifier.height(12.dp))

        ConsejoItem(
            titulo = "Escribe tus pensamientos",
            descripcion = "Poner en palabras lo que sientes puede ayudarte a aclarar emociones."
        )

        Spacer(Modifier.height(12.dp))

        ConsejoItem(
            titulo = "Toma un descanso rápido",
            descripcion = "Si te sientes abrumada/o, detente unos minutos para respirar."
        )

        Spacer(Modifier.height(12.dp))

        ConsejoItem(
            titulo = "Recuerda tu fortaleza",
            descripcion = "Piensa en logros y momentos difíciles que ya superaste."
        )
    }
}

@Composable
fun AyudaItem(titulo: String, descripcion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Color(0xFF7A2C54)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = descripcion,
            fontSize = 14.sp,
            color = Color(0xFF7A2C54)
        )
    }
}

@Composable
fun ConsejoItem(titulo: String, descripcion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(18.dp))
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = Color(0xFF7A2C54)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = descripcion,
            fontSize = 14.sp,
            color = Color(0xFF7A2C54)
        )
    }
}

/* ============================================================
   PANTALLA: DESAFÍOS SEMANALES
   ============================================================ */

@Composable
fun DesafiosSemanalesScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEDF5))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF7A2C54),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Desafíos Semanales",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A2C54)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Los desafíos semanales son pequeñas metas para ayudarte a incorporar hábitos de bienestar en tu rutina.",
            fontSize = 15.sp,
            color = Color(0xFF7A2C54)
        )

        Spacer(Modifier.height(20.dp))

        // Tarjetas de desafíos
        DesafioCard(
            titulo = "Semana de la Atención Plena",
            descripcion = "Practica momentos de mindfulness cada día: observa tu respiración, tus sentidos o lo que estás haciendo."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Auto-Cuidado",
            descripcion = "Incluye una acción de cuidado diario: hidratarte, dormir mejor, estirarte, tomar tu medicación, etc."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Bienestar Físico",
            descripcion = "Muévete un poco cada día: caminar, bailar, hacer ejercicios suaves o salir a dar un paseo."
        )

        Spacer(Modifier.height(12.dp))

        DesafioCard(
            titulo = "Semana del Manejo del Estrés",
            descripcion = "Prueba estrategias para bajar el estrés: respiraciones profundas, pausas activas o escribir lo que sientes."
        )
    }
}

@Composable
fun DesafioCard(
    titulo: String,
    descripcion: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFCE7EF), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circulito a modo de icono
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(Color(0xFFFFA5C8), CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF7A2C54)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color(0xFF7A2C54)
            )
        }
    }
}
